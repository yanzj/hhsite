<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Word.Document"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
	<pkg:part pkg:name="/_rels/.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="512">
		<pkg:xmlData>
			<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
				<Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
				<Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
				<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
				<Relationship Id="rId4" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties" Target="docProps/custom.xml"/>
			</Relationships>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/_rels/document.xml.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="256">
		<pkg:xmlData>
			<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
				<Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings" Target="webSettings.xml"/>
				<Relationship Id="rId7" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme" Target="theme/theme1.xml"/>
				<Relationship Id="rId2" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings" Target="settings.xml"/>
				<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
				<Relationship Id="rId6" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable" Target="fontTable.xml"/>
				<Relationship Id="rId5" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/endnotes" Target="endnotes.xml"/>
				<Relationship Id="rId4" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/footnotes" Target="footnotes.xml"/>
			</Relationships>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/document.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml">
		<pkg:xmlData>
			<w:document xmlns:ve="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml">
				<w:background w:color="FFFFFF"/>
				<w:body>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:jc w:val="center"/>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="36"/>
								<w:szCs w:val="36"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="36"/>
								<w:szCs w:val="36"/>
							</w:rPr>
							<w:t>承诺书</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t xml:space="preserve">    </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>本人</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">  </w:t>
						</w:r>
						<w:r w:rsidR="00970371">
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<#assign ftlNum = 0 />
							<#list loanPeopleList as people>
							<w:t><#if ftlNum &gt; 0>，</#if><#if people.loaners>${people.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></w:t>
							</#list>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">  </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>因生意需要周转向</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">        </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>借款人民币</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">                   </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>并以</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">     </w:t>
						</w:r>
						<w:r w:rsidR="00434C86" w:rsidRPr="00A802F9">
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<#assign ftlNum = 0 />
							<#list loanPawnList as loanPawn>
								<w:t><#if ftlNum &gt; 0>，</#if>${loanPawn.propertyAddress?if_exists}<#assign ftlNum = ftlNum + 1 /></w:t>
							</#list>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">     </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>房产作为抵押，定于</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">      </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>年</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">   </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>月</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">   </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>日前归还所有借款，</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>(</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>借款期限应从借款实际发放日起算，且借款到期日相应顺延。</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>)</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>如逾期不付，本人愿意承担违约金</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve"> 10 </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>%</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>每月，如未履行合同则由抵押物的权利人配合出借方或委托方全权处理该房产并配合迁出户口。</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t xml:space="preserve">    </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>本人确认所提供之所有材料原件以及复印件均真实有效，如有虚假，本人愿意承担一切刑事以及民事责任并赔偿</w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
								<w:u w:val="single"/>
							</w:rPr>
							<w:t xml:space="preserve">        </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>一切经济损失。</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t xml:space="preserve">                                       </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="30"/>
								<w:szCs w:val="30"/>
							</w:rPr>
							<w:t>特此承诺</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
							<w:t xml:space="preserve">                              </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
							<w:t>承诺人：</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
						</w:pPr>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
							<w:t xml:space="preserve">                              </w:t>
						</w:r>
						<w:r>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
								<w:sz w:val="32"/>
								<w:szCs w:val="32"/>
							</w:rPr>
							<w:t>日期：</w:t>
						</w:r>
					</w:p>
					<w:p w:rsidR="009A4D91" w:rsidRDefault="009A4D91">
						<w:pPr>
							<w:rPr>
								<w:rFonts w:hint="eastAsia"/>
							</w:rPr>
						</w:pPr>
					</w:p>
					<w:sectPr w:rsidR="009A4D91">
						<w:pgSz w:w="11906" w:h="16838"/>
						<w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992" w:gutter="0"/>
						<w:cols w:space="720"/>
						<w:docGrid w:type="lines" w:linePitch="312"/>
					</w:sectPr>
				</w:body>
			</w:document>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/footnotes.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml">
		<pkg:xmlData>
			<w:footnotes xmlns:ve="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml">
				<w:footnote w:type="separator" w:id="-1">
					<w:p w:rsidR="003D2165" w:rsidRDefault="003D2165" w:rsidP="00A802F9">
						<w:r>
							<w:separator/>
						</w:r>
					</w:p>
				</w:footnote>
				<w:footnote w:type="continuationSeparator" w:id="0">
					<w:p w:rsidR="003D2165" w:rsidRDefault="003D2165" w:rsidP="00A802F9">
						<w:r>
							<w:continuationSeparator/>
						</w:r>
					</w:p>
				</w:footnote>
			</w:footnotes>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/endnotes.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml">
		<pkg:xmlData>
			<w:endnotes xmlns:ve="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml">
				<w:endnote w:type="separator" w:id="-1">
					<w:p w:rsidR="003D2165" w:rsidRDefault="003D2165" w:rsidP="00A802F9">
						<w:r>
							<w:separator/>
						</w:r>
					</w:p>
				</w:endnote>
				<w:endnote w:type="continuationSeparator" w:id="0">
					<w:p w:rsidR="003D2165" w:rsidRDefault="003D2165" w:rsidP="00A802F9">
						<w:r>
							<w:continuationSeparator/>
						</w:r>
					</w:p>
				</w:endnote>
			</w:endnotes>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/theme/theme1.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml">
		<pkg:xmlData>
			<a:theme name="Office 主题" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
				<a:themeElements>
					<a:clrScheme name="Office">
						<a:dk1>
							<a:sysClr val="windowText" lastClr="000000"/>
						</a:dk1>
						<a:lt1>
							<a:sysClr val="window" lastClr="FFFFFF"/>
						</a:lt1>
						<a:dk2>
							<a:srgbClr val="1F497D"/>
						</a:dk2>
						<a:lt2>
							<a:srgbClr val="EEECE1"/>
						</a:lt2>
						<a:accent1>
							<a:srgbClr val="4F81BD"/>
						</a:accent1>
						<a:accent2>
							<a:srgbClr val="C0504D"/>
						</a:accent2>
						<a:accent3>
							<a:srgbClr val="9BBB59"/>
						</a:accent3>
						<a:accent4>
							<a:srgbClr val="8064A2"/>
						</a:accent4>
						<a:accent5>
							<a:srgbClr val="4BACC6"/>
						</a:accent5>
						<a:accent6>
							<a:srgbClr val="F79646"/>
						</a:accent6>
						<a:hlink>
							<a:srgbClr val="0000FF"/>
						</a:hlink>
						<a:folHlink>
							<a:srgbClr val="800080"/>
						</a:folHlink>
					</a:clrScheme>
					<a:fontScheme name="Office">
						<a:majorFont>
							<a:latin typeface="Cambria"/>
							<a:ea typeface=""/>
							<a:cs typeface=""/>
							<a:font script="Jpan" typeface="ＭＳ ゴシック"/>
							<a:font script="Hang" typeface="맑은 고딕"/>
							<a:font script="Hans" typeface="宋体"/>
							<a:font script="Hant" typeface="新細明體"/>
							<a:font script="Arab" typeface="Times New Roman"/>
							<a:font script="Hebr" typeface="Times New Roman"/>
							<a:font script="Thai" typeface="Angsana New"/>
							<a:font script="Ethi" typeface="Nyala"/>
							<a:font script="Beng" typeface="Vrinda"/>
							<a:font script="Gujr" typeface="Shruti"/>
							<a:font script="Khmr" typeface="MoolBoran"/>
							<a:font script="Knda" typeface="Tunga"/>
							<a:font script="Guru" typeface="Raavi"/>
							<a:font script="Cans" typeface="Euphemia"/>
							<a:font script="Cher" typeface="Plantagenet Cherokee"/>
							<a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
							<a:font script="Tibt" typeface="Microsoft Himalaya"/>
							<a:font script="Thaa" typeface="MV Boli"/>
							<a:font script="Deva" typeface="Mangal"/>
							<a:font script="Telu" typeface="Gautami"/>
							<a:font script="Taml" typeface="Latha"/>
							<a:font script="Syrc" typeface="Estrangelo Edessa"/>
							<a:font script="Orya" typeface="Kalinga"/>
							<a:font script="Mlym" typeface="Kartika"/>
							<a:font script="Laoo" typeface="DokChampa"/>
							<a:font script="Sinh" typeface="Iskoola Pota"/>
							<a:font script="Mong" typeface="Mongolian Baiti"/>
							<a:font script="Viet" typeface="Times New Roman"/>
							<a:font script="Uigh" typeface="Microsoft Uighur"/>
						</a:majorFont>
						<a:minorFont>
							<a:latin typeface="Calibri"/>
							<a:ea typeface=""/>
							<a:cs typeface=""/>
							<a:font script="Jpan" typeface="ＭＳ 明朝"/>
							<a:font script="Hang" typeface="맑은 고딕"/>
							<a:font script="Hans" typeface="宋体"/>
							<a:font script="Hant" typeface="新細明體"/>
							<a:font script="Arab" typeface="Arial"/>
							<a:font script="Hebr" typeface="Arial"/>
							<a:font script="Thai" typeface="Cordia New"/>
							<a:font script="Ethi" typeface="Nyala"/>
							<a:font script="Beng" typeface="Vrinda"/>
							<a:font script="Gujr" typeface="Shruti"/>
							<a:font script="Khmr" typeface="DaunPenh"/>
							<a:font script="Knda" typeface="Tunga"/>
							<a:font script="Guru" typeface="Raavi"/>
							<a:font script="Cans" typeface="Euphemia"/>
							<a:font script="Cher" typeface="Plantagenet Cherokee"/>
							<a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
							<a:font script="Tibt" typeface="Microsoft Himalaya"/>
							<a:font script="Thaa" typeface="MV Boli"/>
							<a:font script="Deva" typeface="Mangal"/>
							<a:font script="Telu" typeface="Gautami"/>
							<a:font script="Taml" typeface="Latha"/>
							<a:font script="Syrc" typeface="Estrangelo Edessa"/>
							<a:font script="Orya" typeface="Kalinga"/>
							<a:font script="Mlym" typeface="Kartika"/>
							<a:font script="Laoo" typeface="DokChampa"/>
							<a:font script="Sinh" typeface="Iskoola Pota"/>
							<a:font script="Mong" typeface="Mongolian Baiti"/>
							<a:font script="Viet" typeface="Arial"/>
							<a:font script="Uigh" typeface="Microsoft Uighur"/>
						</a:minorFont>
					</a:fontScheme>
					<a:fmtScheme name="Office">
						<a:fillStyleLst>
							<a:solidFill>
								<a:schemeClr val="phClr"/>
							</a:solidFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:tint val="50000"/>
											<a:satMod val="300000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="35000">
										<a:schemeClr val="phClr">
											<a:tint val="37000"/>
											<a:satMod val="300000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:tint val="15000"/>
											<a:satMod val="350000"/>
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:lin ang="16200000" scaled="1"/>
							</a:gradFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:shade val="51000"/>
											<a:satMod val="130000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="80000">
										<a:schemeClr val="phClr">
											<a:shade val="93000"/>
											<a:satMod val="130000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:shade val="94000"/>
											<a:satMod val="135000"/>
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:lin ang="16200000" scaled="0"/>
							</a:gradFill>
						</a:fillStyleLst>
						<a:lnStyleLst>
							<a:ln w="9525" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr">
										<a:shade val="95000"/>
										<a:satMod val="105000"/>
									</a:schemeClr>
								</a:solidFill>
								<a:prstDash val="solid"/>
							</a:ln>
							<a:ln w="25400" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr"/>
								</a:solidFill>
								<a:prstDash val="solid"/>
							</a:ln>
							<a:ln w="38100" cap="flat" cmpd="sng" algn="ctr">
								<a:solidFill>
									<a:schemeClr val="phClr"/>
								</a:solidFill>
								<a:prstDash val="solid"/>
							</a:ln>
						</a:lnStyleLst>
						<a:effectStyleLst>
							<a:effectStyle>
								<a:effectLst>
									<a:outerShdw blurRad="40000" dist="20000" dir="5400000" rotWithShape="0">
										<a:srgbClr val="000000">
											<a:alpha val="38000"/>
										</a:srgbClr>
									</a:outerShdw>
								</a:effectLst>
							</a:effectStyle>
							<a:effectStyle>
								<a:effectLst>
									<a:outerShdw blurRad="40000" dist="23000" dir="5400000" rotWithShape="0">
										<a:srgbClr val="000000">
											<a:alpha val="35000"/>
										</a:srgbClr>
									</a:outerShdw>
								</a:effectLst>
							</a:effectStyle>
							<a:effectStyle>
								<a:effectLst>
									<a:outerShdw blurRad="40000" dist="23000" dir="5400000" rotWithShape="0">
										<a:srgbClr val="000000">
											<a:alpha val="35000"/>
										</a:srgbClr>
									</a:outerShdw>
								</a:effectLst>
								<a:scene3d>
									<a:camera prst="orthographicFront">
										<a:rot lat="0" lon="0" rev="0"/>
									</a:camera>
									<a:lightRig rig="threePt" dir="t">
										<a:rot lat="0" lon="0" rev="1200000"/>
									</a:lightRig>
								</a:scene3d>
								<a:sp3d>
									<a:bevelT w="63500" h="25400"/>
								</a:sp3d>
							</a:effectStyle>
						</a:effectStyleLst>
						<a:bgFillStyleLst>
							<a:solidFill>
								<a:schemeClr val="phClr"/>
							</a:solidFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:tint val="40000"/>
											<a:satMod val="350000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="40000">
										<a:schemeClr val="phClr">
											<a:tint val="45000"/>
											<a:shade val="99000"/>
											<a:satMod val="350000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:shade val="20000"/>
											<a:satMod val="255000"/>
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:path path="circle">
									<a:fillToRect l="50000" t="-80000" r="50000" b="180000"/>
								</a:path>
							</a:gradFill>
							<a:gradFill rotWithShape="1">
								<a:gsLst>
									<a:gs pos="0">
										<a:schemeClr val="phClr">
											<a:tint val="80000"/>
											<a:satMod val="300000"/>
										</a:schemeClr>
									</a:gs>
									<a:gs pos="100000">
										<a:schemeClr val="phClr">
											<a:shade val="30000"/>
											<a:satMod val="200000"/>
										</a:schemeClr>
									</a:gs>
								</a:gsLst>
								<a:path path="circle">
									<a:fillToRect l="50000" t="50000" r="50000" b="50000"/>
								</a:path>
							</a:gradFill>
						</a:bgFillStyleLst>
					</a:fmtScheme>
				</a:themeElements>
				<a:objectDefaults/>
				<a:extraClrSchemeLst/>
			</a:theme>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/settings.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
		<pkg:xmlData>
			<w:settings xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main">
				<w:zoom w:percent="100"/>
				<w:bordersDoNotSurroundHeader/>
				<w:bordersDoNotSurroundFooter/>
				<w:stylePaneFormatFilter w:val="3F01"/>
				<w:doNotTrackMoves/>
				<w:defaultTabStop w:val="420"/>
				<w:drawingGridVerticalSpacing w:val="156"/>
				<w:displayHorizontalDrawingGridEvery w:val="0"/>
				<w:displayVerticalDrawingGridEvery w:val="2"/>
				<w:characterSpacingControl w:val="compressPunctuation"/>
				<w:doNotValidateAgainstSchema/>
				<w:doNotDemarcateInvalidXml/>
				<w:hdrShapeDefaults>
					<o:shapedefaults v:ext="edit" spidmax="3074" strokecolor="#739cc3">
						<v:fill angle="90" type="gradient">
							<o:fill v:ext="view" type="gradientUnscaled"/>
						</v:fill>
						<v:stroke color="#739cc3" weight="1.25pt"/>
					</o:shapedefaults>
				</w:hdrShapeDefaults>
				<w:footnotePr>
					<w:footnote w:id="-1"/>
					<w:footnote w:id="0"/>
				</w:footnotePr>
				<w:endnotePr>
					<w:endnote w:id="-1"/>
					<w:endnote w:id="0"/>
				</w:endnotePr>
				<w:compat>
					<w:spaceForUL/>
					<w:balanceSingleByteDoubleByteWidth/>
					<w:doNotLeaveBackslashAlone/>
					<w:ulTrailSpace/>
					<w:doNotExpandShiftReturn/>
					<w:wrapTrailSpaces/>
					<w:adjustLineHeightInTable/>
					<w:useFELayout/>
					<w:useNormalStyleForList/>
					<w:doNotUseIndentAsNumberingTabStop/>
					<w:useAltKinsokuLineBreakRules/>
					<w:allowSpaceOfSameStyleInTable/>
					<w:doNotSuppressIndentation/>
					<w:doNotAutofitConstrainedTables/>
					<w:autofitToFirstFixedWidthCell/>
					<w:displayHangulFixedWidth/>
					<w:splitPgBreakAndParaMark/>
					<w:doNotVertAlignCellWithSp/>
					<w:doNotBreakConstrainedForcedTable/>
					<w:doNotVertAlignInTxbx/>
					<w:useAnsiKerningPairs/>
					<w:cachedColBalance/>
				</w:compat>
				<w:rsids>
					<w:rsidRoot w:val="00172A27"/>
					<w:rsid w:val="002B0066"/>
					<w:rsid w:val="003D2165"/>
					<w:rsid w:val="00434C86"/>
					<w:rsid w:val="005B0079"/>
					<w:rsid w:val="00652B43"/>
					<w:rsid w:val="00956E06"/>
					<w:rsid w:val="00970371"/>
					<w:rsid w:val="009A4D91"/>
					<w:rsid w:val="00A74F83"/>
					<w:rsid w:val="00A802F9"/>
					<w:rsid w:val="00B438B6"/>
					<w:rsid w:val="00E362A0"/>
					<w:rsid w:val="00E8045D"/>
					<w:rsid w:val="00E8710E"/>
					<w:rsid w:val="00EF625D"/>
					<w:rsid w:val="00FD7AEE"/>
					<w:rsid w:val="4E2A48D3"/>
				</w:rsids>
				<m:mathPr>
					<m:mathFont m:val="Cambria Math"/>
					<m:brkBin m:val="before"/>
					<m:brkBinSub m:val="--"/>
					<m:smallFrac m:val="off"/>
					<m:dispDef/>
					<m:lMargin m:val="0"/>
					<m:rMargin m:val="0"/>
					<m:defJc m:val="centerGroup"/>
					<m:wrapIndent m:val="1440"/>
					<m:intLim m:val="subSup"/>
					<m:naryLim m:val="undOvr"/>
				</m:mathPr>
				<w:uiCompat97To2003/>
				<w:themeFontLang w:val="en-US" w:eastAsia="zh-CN"/>
				<w:clrSchemeMapping w:bg1="light1" w:t1="dark1" w:bg2="light2" w:t2="dark2" w:accent1="accent1" w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5" w:accent6="accent6" w:hyperlink="hyperlink" w:followedHyperlink="followedHyperlink"/>
				<w:doNotIncludeSubdocsInStats/>
				<w:shapeDefaults>
					<o:shapedefaults v:ext="edit" spidmax="3074" strokecolor="#739cc3">
						<v:fill angle="90" type="gradient">
							<o:fill v:ext="view" type="gradientUnscaled"/>
						</v:fill>
						<v:stroke color="#739cc3" weight="1.25pt"/>
					</o:shapedefaults>
					<o:shapelayout v:ext="edit">
						<o:idmap v:ext="edit" data="1"/>
					</o:shapelayout>
				</w:shapeDefaults>
				<w:decimalSymbol w:val="."/>
				<w:listSeparator w:val=","/>
			</w:settings>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/webSettings.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml">
		<pkg:xmlData>
			<w:webSettings xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
				<w:encoding w:val="x-cp20936"/>
				<w:optimizeForBrowser/>
				<w:allowPNG/>
				<w:targetScreenSz w:val="1024x768"/>
			</w:webSettings>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/custom.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.custom-properties+xml" pkg:padding="256">
		<pkg:xmlData>
			<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/custom-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
				<property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}" pid="2" name="KSOProductBuildVer">
					<vt:lpwstr>2052-10.1.0.6391</vt:lpwstr>
				</property>
			</Properties>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/core.xml" pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml" pkg:padding="256">
		<pkg:xmlData>
			<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<dc:title>承诺书</dc:title>
				<dc:creator>Administrator</dc:creator>
				<cp:lastModifiedBy>dell</cp:lastModifiedBy>
				<cp:revision>2</cp:revision>
				<cp:lastPrinted>2015-02-09T09:20:00Z</cp:lastPrinted>
				<dcterms:created xsi:type="dcterms:W3CDTF">2017-08-18T09:07:00Z</dcterms:created>
				<dcterms:modified xsi:type="dcterms:W3CDTF">2017-08-18T09:07:00Z</dcterms:modified>
			</cp:coreProperties>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/styles.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
		<pkg:xmlData>
			<w:styles xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
				<w:docDefaults>
					<w:rPrDefault>
						<w:rPr>
							<w:rFonts w:ascii="Times New Roman" w:eastAsia="宋体" w:hAnsi="Times New Roman" w:cs="Times New Roman"/>
							<w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
						</w:rPr>
					</w:rPrDefault>
					<w:pPrDefault/>
				</w:docDefaults>
				<w:latentStyles w:defLockedState="0" w:defUIPriority="0" w:defSemiHidden="0" w:defUnhideWhenUsed="0" w:defQFormat="0" w:count="267">
					<w:lsdException w:name="Normal" w:qFormat="1"/>
					<w:lsdException w:name="heading 1" w:qFormat="1"/>
					<w:lsdException w:name="heading 2" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 3" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 4" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 5" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 6" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 7" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 8" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="heading 9" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="caption" w:semiHidden="1" w:unhideWhenUsed="1" w:qFormat="1"/>
					<w:lsdException w:name="Title" w:qFormat="1"/>
					<w:lsdException w:name="Subtitle" w:qFormat="1"/>
					<w:lsdException w:name="Strong" w:qFormat="1"/>
					<w:lsdException w:name="Emphasis" w:qFormat="1"/>
					<w:lsdException w:name="HTML Top of Form" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="HTML Bottom of Form" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Normal Table" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="No List" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Outline List 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Outline List 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Outline List 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Simple 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Simple 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Simple 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Classic 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Classic 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Classic 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Classic 4" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Colorful 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Colorful 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Colorful 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Columns 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Columns 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Columns 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Columns 4" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Columns 5" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 4" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 5" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 6" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 7" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid 8" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 4" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 5" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 6" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 7" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table List 8" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table 3D effects 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table 3D effects 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table 3D effects 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Contemporary" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Elegant" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Professional" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Subtle 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Subtle 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Web 1" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Web 2" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Web 3" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Grid" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Table Theme" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="Placeholder Text" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="No Spacing" w:uiPriority="99" w:qFormat="1"/>
					<w:lsdException w:name="Light Shading" w:uiPriority="60"/>
					<w:lsdException w:name="Light List" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 1" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 1" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 1" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 1" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 1" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 1" w:uiPriority="65"/>
					<w:lsdException w:name="Revision" w:semiHidden="1" w:uiPriority="99" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="List Paragraph" w:uiPriority="99" w:qFormat="1"/>
					<w:lsdException w:name="Quote" w:uiPriority="99" w:qFormat="1"/>
					<w:lsdException w:name="Intense Quote" w:uiPriority="99" w:qFormat="1"/>
					<w:lsdException w:name="Medium List 2 Accent 1" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 1" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 1" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 1" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 1" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 1" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 1" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 1" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 2" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 2" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 2" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 2" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 2" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 2" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2 Accent 2" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 2" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 2" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 2" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 2" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 2" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 2" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 2" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 3" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 3" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 3" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 3" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 3" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 3" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2 Accent 3" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 3" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 3" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 3" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 3" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 3" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 3" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 3" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 4" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 4" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 4" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 4" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 4" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 4" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2 Accent 4" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 4" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 4" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 4" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 4" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 4" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 4" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 4" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 5" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 5" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 5" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 5" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 5" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 5" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2 Accent 5" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 5" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 5" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 5" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 5" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 5" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 5" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 5" w:uiPriority="73"/>
					<w:lsdException w:name="Light Shading Accent 6" w:uiPriority="60"/>
					<w:lsdException w:name="Light List Accent 6" w:uiPriority="61"/>
					<w:lsdException w:name="Light Grid Accent 6" w:uiPriority="62"/>
					<w:lsdException w:name="Medium Shading 1 Accent 6" w:uiPriority="63"/>
					<w:lsdException w:name="Medium Shading 2 Accent 6" w:uiPriority="64"/>
					<w:lsdException w:name="Medium List 1 Accent 6" w:uiPriority="65"/>
					<w:lsdException w:name="Medium List 2 Accent 6" w:uiPriority="66"/>
					<w:lsdException w:name="Medium Grid 1 Accent 6" w:uiPriority="67"/>
					<w:lsdException w:name="Medium Grid 2 Accent 6" w:uiPriority="68"/>
					<w:lsdException w:name="Medium Grid 3 Accent 6" w:uiPriority="69"/>
					<w:lsdException w:name="Dark List Accent 6" w:uiPriority="70"/>
					<w:lsdException w:name="Colorful Shading Accent 6" w:uiPriority="71"/>
					<w:lsdException w:name="Colorful List Accent 6" w:uiPriority="72"/>
					<w:lsdException w:name="Colorful Grid Accent 6" w:uiPriority="73"/>
					<w:lsdException w:name="Subtle Emphasis" w:uiPriority="19" w:qFormat="1"/>
					<w:lsdException w:name="Intense Emphasis" w:uiPriority="21" w:qFormat="1"/>
					<w:lsdException w:name="Subtle Reference" w:uiPriority="31" w:qFormat="1"/>
					<w:lsdException w:name="Intense Reference" w:uiPriority="32" w:qFormat="1"/>
					<w:lsdException w:name="Book Title" w:uiPriority="33" w:qFormat="1"/>
					<w:lsdException w:name="Bibliography" w:semiHidden="1" w:uiPriority="37" w:unhideWhenUsed="1"/>
					<w:lsdException w:name="TOC Heading" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1" w:qFormat="1"/>
				</w:latentStyles>
				<w:style w:type="paragraph" w:default="1" w:styleId="a">
					<w:name w:val="Normal"/>
					<w:qFormat/>
					<w:pPr>
						<w:widowControl w:val="0"/>
						<w:jc w:val="both"/>
					</w:pPr>
					<w:rPr>
						<w:kern w:val="2"/>
						<w:sz w:val="21"/>
					</w:rPr>
				</w:style>
				<w:style w:type="character" w:default="1" w:styleId="a0">
					<w:name w:val="Default Paragraph Font"/>
				</w:style>
				<w:style w:type="table" w:default="1" w:styleId="a1">
					<w:name w:val="Normal Table"/>
					<w:uiPriority w:val="99"/>
					<w:unhideWhenUsed/>
					<w:tblPr>
						<w:tblCellMar>
							<w:top w:w="0" w:type="dxa"/>
							<w:left w:w="108" w:type="dxa"/>
							<w:bottom w:w="0" w:type="dxa"/>
							<w:right w:w="108" w:type="dxa"/>
						</w:tblCellMar>
					</w:tblPr>
				</w:style>
				<w:style w:type="numbering" w:default="1" w:styleId="a2">
					<w:name w:val="No List"/>
					<w:uiPriority w:val="99"/>
					<w:semiHidden/>
					<w:unhideWhenUsed/>
				</w:style>
				<w:style w:type="paragraph" w:styleId="a3">
					<w:name w:val="header"/>
					<w:basedOn w:val="a"/>
					<w:pPr>
						<w:pBdr>
							<w:top w:val="none" w:sz="0" w:space="1" w:color="auto"/>
							<w:left w:val="none" w:sz="0" w:space="4" w:color="auto"/>
							<w:bottom w:val="none" w:sz="0" w:space="1" w:color="auto"/>
							<w:right w:val="none" w:sz="0" w:space="4" w:color="auto"/>
						</w:pBdr>
						<w:tabs>
							<w:tab w:val="center" w:pos="4153"/>
							<w:tab w:val="right" w:pos="8306"/>
						</w:tabs>
						<w:snapToGrid w:val="0"/>
					</w:pPr>
					<w:rPr>
						<w:sz w:val="18"/>
					</w:rPr>
				</w:style>
				<w:style w:type="paragraph" w:styleId="a4">
					<w:name w:val="footer"/>
					<w:basedOn w:val="a"/>
					<w:pPr>
						<w:tabs>
							<w:tab w:val="center" w:pos="4153"/>
							<w:tab w:val="right" w:pos="8306"/>
						</w:tabs>
						<w:snapToGrid w:val="0"/>
						<w:jc w:val="left"/>
					</w:pPr>
					<w:rPr>
						<w:sz w:val="18"/>
					</w:rPr>
				</w:style>
			</w:styles>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/word/fontTable.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
		<pkg:xmlData>
			<w:fonts xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
				<w:font w:name="Times New Roman">
					<w:panose1 w:val="02020603050405020304"/>
					<w:charset w:val="00"/>
					<w:family w:val="roman"/>
					<w:pitch w:val="variable"/>
					<w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
				</w:font>
				<w:font w:name="宋体">
					<w:altName w:val="SimSun"/>
					<w:panose1 w:val="02010600030101010101"/>
					<w:charset w:val="86"/>
					<w:family w:val="auto"/>
					<w:pitch w:val="variable"/>
					<w:sig w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001" w:csb1="00000000"/>
				</w:font>
				<w:font w:name="Cambria">
					<w:panose1 w:val="02040503050406030204"/>
					<w:charset w:val="00"/>
					<w:family w:val="roman"/>
					<w:pitch w:val="variable"/>
					<w:sig w:usb0="E00002FF" w:usb1="400004FF" w:usb2="00000000" w:usb3="00000000" w:csb0="0000019F" w:csb1="00000000"/>
				</w:font>
				<w:font w:name="Calibri">
					<w:panose1 w:val="020F0502020204030204"/>
					<w:charset w:val="00"/>
					<w:family w:val="swiss"/>
					<w:pitch w:val="variable"/>
					<w:sig w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
				</w:font>
			</w:fonts>
		</pkg:xmlData>
	</pkg:part>
	<pkg:part pkg:name="/docProps/app.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" pkg:padding="256">
		<pkg:xmlData>
			<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
				<Template>Normal.dotm</Template>
				<TotalTime>0</TotalTime>
				<Pages>1</Pages>
				<Words>58</Words>
				<Characters>331</Characters>
				<Application>Microsoft Office Word</Application>
				<DocSecurity>0</DocSecurity>
				<PresentationFormat/>
				<Lines>2</Lines>
				<Paragraphs>1</Paragraphs>
				<Slides>0</Slides>
				<Notes>0</Notes>
				<HiddenSlides>0</HiddenSlides>
				<MMClips>0</MMClips>
				<ScaleCrop>false</ScaleCrop>
				<Company/>
				<LinksUpToDate>false</LinksUpToDate>
				<CharactersWithSpaces>388</CharactersWithSpaces>
				<SharedDoc>false</SharedDoc>
				<HyperlinksChanged>false</HyperlinksChanged>
				<AppVersion>12.0000</AppVersion>
			</Properties>
		</pkg:xmlData>
	</pkg:part>
</pkg:package>