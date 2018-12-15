package com.vilio.wct.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlUtil {
	/**
	 * map转成xml
	 * 
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToXML(Map map) throws IOException {
		Element root = new Element("xml");
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		xmlout.output(XmlUtil.parseToXML(map, root), bo);
		// return bo.toString();
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + bo.toString();
	}

	/**
	 * map转成xml
	 * 
	 * @param map
	 *            map值
	 * @param element
	 *            根节点
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Element parseToXML(Map map, Element element) {
		/** 开始对map进行解析 */
		if (map == null)
			throw new NullPointerException("map 数据为空,不能解析!");
		Set set = map.entrySet();
		Iterator records = set.iterator();
		while (records.hasNext()) {
			Map.Entry entry = (Map.Entry) records.next();
			if (entry.getValue().getClass().equals(HashMap.class)) {// 子目录
				Element node = new Element(entry.getKey().toString());
				element.addContent(node);
				parseToXML((HashMap) entry.getValue(), node);
			} else if (entry.getValue().getClass().equals(ArrayList.class)) {
				List<Element> list2 = listToXml((List) entry.getValue(), entry.getKey().toString());
				for (Element listElement : list2) {
					element.addContent(listElement);
				}
			} else {// 到达顶点
				Element node = new Element(entry.getKey().toString());
				node.setText(entry.getValue().toString());
				element.addContent(node);
			}
		}
		return element;
	}

	public static List listToXml(List<Map> list, String nodeName) {
		List element = new ArrayList();
		for (Map map : list) {
			Element nodeTop = new Element(nodeName);
			if (map == null)
				throw new NullPointerException("map 数据为空,不能解析!");
			Set set = map.entrySet();
			Iterator records = set.iterator();
			while (records.hasNext()) {
				Map.Entry entry = (Map.Entry) records.next();
				if (entry.getValue().getClass().equals(HashMap.class)) {// 子目录
					Element node = new Element(entry.getKey().toString());
					nodeTop.addContent(node);
					parseToXML((HashMap) entry.getValue(), node);
				} else if (entry.getValue().getClass().equals(ArrayList.class)) {
					List<Element> list2 = listToXml((List) entry.getValue(), entry.getKey().toString());
					for (Element listElement : list2) {
						nodeTop.addContent(listElement);
					}
				} else {// 到达顶点
					Element node = new Element(entry.getKey().toString());
					node.setText(entry.getValue().toString());
					nodeTop.addContent(node);
				}
			}
			element.add(nodeTop);
		}

		return element;
	}

	/**
	 * xml字符串转换成map值
	 * 
	 * @param xmlStr
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, Object> XmlToMap(String xmlStr) throws JDOMException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		java.io.Reader in = new StringReader(xmlStr);
		Document doc = (new SAXBuilder()).build(in);
		map = XmlUtil.getResultMap(doc, "");
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getResultMap(Document doc, String filterStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.getChildren().iterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.getChildren();
			if (list.size() > 0) {
				map.put(e.getName(), getChildMap(e, filterStr));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getChildMap(Element e, String filterStr) {
		Map map = new HashMap();
		List list = e.getChildren();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.getChildren().size() > 0) {
					if (validateName(iter, filterStr)) {// 过滤在filterStr中包含的值
						Map m = getChildMap(iter, filterStr);
						if (map.get(iter.getName()) != null) {
							Object obj = map.get(iter.getName());
							if (!obj.getClass().getName().equals("java.util.ArrayList")) {
								mapList = new ArrayList();
								mapList.add(obj);
								mapList.add(m);
							}
							if (obj.getClass().getName().equals("java.util.ArrayList")) {
								mapList = (List) obj;
								mapList.add(m);
							}
							map.put(iter.getName(), mapList);
						} else
							map.put(iter.getName(), m);
					}

				} else {
					if (validateName(iter, filterStr)) {// 过滤在filterStr中包含的值
						if (map.get(iter.getName()) != null) {
							Object obj = map.get(iter.getName());
							if (!obj.getClass().getName().equals("java.util.ArrayList")) {
								mapList = new ArrayList();
								mapList.add(obj);
								mapList.add(iter.getText());
							}
							if (obj.getClass().getName().equals("java.util.ArrayList")) {
								mapList = (List) obj;
								mapList.add(iter.getText());
							}
							map.put(iter.getName(), mapList);
						} else
							map.put(iter.getName(), iter.getText());
					}

				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/** 判断含有子节点的name子节点的值或单个节点的值是否在filterContent中存在 */
	@SuppressWarnings("rawtypes")
	public static boolean validateName(Element iter, String filterContent) {
		List children = iter.getChildren();
		if (children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("name".equals(child.getName()) && filterContent.contains(child.getText())) {
					return false;
				}
			}
		} else {
			if (filterContent.contains(iter.getText())) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		String xmlStr = "<xml><ToUserName><![CDATA[gh_e567d03cc3fa]]></ToUserName>"
				+ "<FromUserName><![CDATA[opOyF1pZJHcs5HroTijr5DfDwFIQ]]></FromUserName>"
				+ "<CreateTime>1496910808</CreateTime><MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[ff]]></Content><MsgId>6429182965834511935</MsgId></xml>";
		try {
			System.out.println(XmlToMap(xmlStr));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}