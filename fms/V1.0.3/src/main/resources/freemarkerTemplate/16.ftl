<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Word.Document"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
  <pkg:part pkg:name="/_rels/.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="512">
    <pkg:xmlData>
      <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
        <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
        <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
        <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
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
        <w:body>
          <w:p w:rsidR="000E796D" w:rsidRPr="0032343C" w:rsidRDefault="000C7874" w:rsidP="000C7874">
            <w:pPr>
              <w:jc w:val="center"/>
              <w:rPr>
                <w:b/>
                <w:sz w:val="40"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="0032343C">
              <w:rPr>
                <w:b/>
                <w:sz w:val="40"/>
              </w:rPr>
              <w:t>声明书</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="000C7874" w:rsidRPr="00C07FD9" w:rsidRDefault="000C7874" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>声明人</w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>：<#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt;= 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list></w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t xml:space="preserve"> </w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="000C7874" w:rsidRPr="00C07FD9" w:rsidRDefault="000C7874" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>身份证号码</w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>：<#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt;= 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.certificateNumber?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list></w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t xml:space="preserve"> </w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="000D585B" w:rsidRDefault="000C7874" w:rsidP="00DD1B3D">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:leftChars="267" w:left="841" w:hangingChars="100" w:hanging="280"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>我们</w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"> <#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt;= 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>是儿子（女儿）</w:t>
            </w:r>
            <w:r w:rsidR="00DD1B3D">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
						  <#assign ftlNum = 0 />
							<w:t> <#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt; 0 && loanPawnPeople.fullAge &lt; 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>（</w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t xml:space="preserve"> </w:t>
            </w:r>
            <w:r w:rsidR="00DD1B3D">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t><#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt; 0 && loanPawnPeople.fullAge &lt; 18><#if ftlNum &gt; 0>；</#if>${loanPawnPeople.name?if_exists}，${loanPawnPeople.certificateNumber?substring(6,10)}年${loanPawnPeople.certificateNumber?substring(10,12)}月${loanPawnPeople.certificateNumber?substring(12,14)}日出生，身份证号码：${loanPawnPeople.certificateNumber}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list></w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t xml:space="preserve"> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>）的父母，是其法</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="000C7874" w:rsidRPr="00C07FD9" w:rsidRDefault="000C7874" w:rsidP="000D585B">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>定监护人。位于</w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:ascii="幼圆" w:eastAsia="幼圆" w:hAnsi="楷体_GB2312" w:cs="幼圆" w:hint="eastAsia"/>
                <w:sz w:val="28"/>
                <w:szCs w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"> <#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#if ftlNum &gt; 0>，</#if>${loanPawn.propertyAddress?if_exists}<#assign ftlNum = ftlNum + 1 /></#list> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>的房屋是以</w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"> <#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#list></#list> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>的名义登记</w:t>
            </w:r>
            <w:bookmarkStart w:id="0" w:name="_GoBack"/>
            <w:bookmarkEnd w:id="0"/>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>产权的，</w:t>
            </w:r>
						<w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"><#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt; 0 && loanPawnPeople.fullAge &lt; 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list> </w:t>
            </w:r>
						<w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>占</w:t>
            </w:r>
            <w:r w:rsidR="000D585B">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"><#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#if ftlNum &gt; 0>，</#if>${loanPawn.propertyMinors}<#assign ftlNum = ftlNum + 1 /></#list></w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>产权。因是未成年人，我们作为其法定监护人，代为管理上述房屋。现为了被监护人</w:t>
            </w:r>
            <w:r w:rsidR="00995AE8">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"><#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt; 0 && loanPawnPeople.fullAge &lt; 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list></w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>的教育和生活需要，我们决定抵押上述房屋。我们在此声明：我们</w:t>
            </w:r>
            <w:r w:rsidR="00995AE8">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
                <w:bCs/>
                <w:sz w:val="28"/>
                <w:u w:val="single"/>
              </w:rPr>
              <w:t xml:space="preserve"> <#assign ftlNum = 0 /><#list loanPawnList as loanPawn><#list loanPawn.propertyOwnerList as loanPawnPeople><#if loanPawnPeople.fullAge &gt;= 18><#if ftlNum &gt; 0>，</#if>${loanPawnPeople.name?if_exists}<#assign ftlNum = ftlNum + 1 /></#if></#list></#list> </w:t>
            </w:r>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>保证抵押上述房屋所得的款项将全部作为被监护人的教育和生活费用，我们愿意承担由此产生的一切法律责任。</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="000C7874" w:rsidRDefault="000C7874" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r w:rsidRPr="00C07FD9">
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>特此声明</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="00C07FD9" w:rsidRDefault="00C07FD9" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
          </w:p>
          <w:p w:rsidR="00C07FD9" w:rsidRDefault="00C07FD9" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
          </w:p>
          <w:p w:rsidR="00C07FD9" w:rsidRDefault="00C07FD9" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>声明人</w:t>
            </w:r>
            <w:r>
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>：</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="00C07FD9" w:rsidRDefault="00C07FD9" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r>
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>右手食指指模：</w:t>
            </w:r>
          </w:p>
          <w:p w:rsidR="00C07FD9" w:rsidRPr="00C07FD9" w:rsidRDefault="00C07FD9" w:rsidP="00C07FD9">
            <w:pPr>
              <w:spacing w:line="360" w:lineRule="auto"/>
              <w:ind w:firstLineChars="200" w:firstLine="560"/>
              <w:rPr>
                <w:sz w:val="28"/>
              </w:rPr>
            </w:pPr>
            <w:r>
              <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:sz w:val="28"/>
              </w:rPr>
              <w:t>日期：</w:t>
            </w:r>
          </w:p>
          <w:sectPr w:rsidR="00C07FD9" w:rsidRPr="00C07FD9" w:rsidSect="00CA031B">
            <w:pgSz w:w="11906" w:h="16838"/>
            <w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992" w:gutter="0"/>
            <w:cols w:space="425"/>
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
          <w:p w:rsidR="00193FE7" w:rsidRDefault="00193FE7" w:rsidP="00362272">
            <w:r>
              <w:separator/>
            </w:r>
          </w:p>
        </w:footnote>
        <w:footnote w:type="continuationSeparator" w:id="0">
          <w:p w:rsidR="00193FE7" w:rsidRDefault="00193FE7" w:rsidP="00362272">
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
          <w:p w:rsidR="00193FE7" w:rsidRDefault="00193FE7" w:rsidP="00362272">
            <w:r>
              <w:separator/>
            </w:r>
          </w:p>
        </w:endnote>
        <w:endnote w:type="continuationSeparator" w:id="0">
          <w:p w:rsidR="00193FE7" w:rsidRDefault="00193FE7" w:rsidP="00362272">
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
      <a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" name="Office 主题">
        <a:themeElements>
          <a:clrScheme name="Office">
            <a:dk1>
              <a:sysClr val="windowText" lastClr="000000"/>
            </a:dk1>
            <a:lt1>
              <a:sysClr val="window" lastClr="FFFFFF"/>
            </a:lt1>
            <a:dk2>
              <a:srgbClr val="44546A"/>
            </a:dk2>
            <a:lt2>
              <a:srgbClr val="E7E6E6"/>
            </a:lt2>
            <a:accent1>
              <a:srgbClr val="5B9BD5"/>
            </a:accent1>
            <a:accent2>
              <a:srgbClr val="ED7D31"/>
            </a:accent2>
            <a:accent3>
              <a:srgbClr val="A5A5A5"/>
            </a:accent3>
            <a:accent4>
              <a:srgbClr val="FFC000"/>
            </a:accent4>
            <a:accent5>
              <a:srgbClr val="4472C4"/>
            </a:accent5>
            <a:accent6>
              <a:srgbClr val="70AD47"/>
            </a:accent6>
            <a:hlink>
              <a:srgbClr val="0563C1"/>
            </a:hlink>
            <a:folHlink>
              <a:srgbClr val="954F72"/>
            </a:folHlink>
          </a:clrScheme>
          <a:fontScheme name="Office">
            <a:majorFont>
              <a:latin typeface="Calibri Light"/>
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
              <a:font script="Geor" typeface="Sylfaen"/>
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
              <a:font script="Geor" typeface="Sylfaen"/>
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
                      <a:lumMod val="110000"/>
                      <a:satMod val="105000"/>
                      <a:tint val="67000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="105000"/>
                      <a:satMod val="103000"/>
                      <a:tint val="73000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="105000"/>
                      <a:satMod val="109000"/>
                      <a:tint val="81000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
              <a:gradFill rotWithShape="1">
                <a:gsLst>
                  <a:gs pos="0">
                    <a:schemeClr val="phClr">
                      <a:satMod val="103000"/>
                      <a:lumMod val="102000"/>
                      <a:tint val="94000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:satMod val="110000"/>
                      <a:lumMod val="100000"/>
                      <a:shade val="100000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:lumMod val="99000"/>
                      <a:satMod val="120000"/>
                      <a:shade val="78000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
            </a:fillStyleLst>
            <a:lnStyleLst>
              <a:ln w="6350" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
              <a:ln w="12700" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
              <a:ln w="19050" cap="flat" cmpd="sng" algn="ctr">
                <a:solidFill>
                  <a:schemeClr val="phClr"/>
                </a:solidFill>
                <a:prstDash val="solid"/>
                <a:miter lim="800000"/>
              </a:ln>
            </a:lnStyleLst>
            <a:effectStyleLst>
              <a:effectStyle>
                <a:effectLst/>
              </a:effectStyle>
              <a:effectStyle>
                <a:effectLst/>
              </a:effectStyle>
              <a:effectStyle>
                <a:effectLst>
                  <a:outerShdw blurRad="57150" dist="19050" dir="5400000" algn="ctr" rotWithShape="0">
                    <a:srgbClr val="000000">
                      <a:alpha val="63000"/>
                    </a:srgbClr>
                  </a:outerShdw>
                </a:effectLst>
              </a:effectStyle>
            </a:effectStyleLst>
            <a:bgFillStyleLst>
              <a:solidFill>
                <a:schemeClr val="phClr"/>
              </a:solidFill>
              <a:solidFill>
                <a:schemeClr val="phClr">
                  <a:tint val="95000"/>
                  <a:satMod val="170000"/>
                </a:schemeClr>
              </a:solidFill>
              <a:gradFill rotWithShape="1">
                <a:gsLst>
                  <a:gs pos="0">
                    <a:schemeClr val="phClr">
                      <a:tint val="93000"/>
                      <a:satMod val="150000"/>
                      <a:shade val="98000"/>
                      <a:lumMod val="102000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="50000">
                    <a:schemeClr val="phClr">
                      <a:tint val="98000"/>
                      <a:satMod val="130000"/>
                      <a:shade val="90000"/>
                      <a:lumMod val="103000"/>
                    </a:schemeClr>
                  </a:gs>
                  <a:gs pos="100000">
                    <a:schemeClr val="phClr">
                      <a:shade val="63000"/>
                      <a:satMod val="120000"/>
                    </a:schemeClr>
                  </a:gs>
                </a:gsLst>
                <a:lin ang="5400000" scaled="0"/>
              </a:gradFill>
            </a:bgFillStyleLst>
          </a:fmtScheme>
        </a:themeElements>
        <a:objectDefaults/>
        <a:extraClrSchemeLst/>
        <a:extLst>
          <a:ext uri="{05A4C25C-085E-4340-85A3-A5531E510DB2}">
            <thm15:themeFamily xmlns="" xmlns:thm15="http://schemas.microsoft.com/office/thememl/2012/main" name="Office Theme" id="{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}" vid="{4A3C46E8-61CC-4603-A589-7422A47A8E4A}"/>
          </a:ext>
        </a:extLst>
      </a:theme>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/settings.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
    <pkg:xmlData>
      <w:settings xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main">
        <w:zoom w:percent="100"/>
        <w:bordersDoNotSurroundHeader/>
        <w:bordersDoNotSurroundFooter/>
        <w:proofState w:spelling="clean" w:grammar="clean"/>
        <w:defaultTabStop w:val="420"/>
        <w:drawingGridVerticalSpacing w:val="156"/>
        <w:displayHorizontalDrawingGridEvery w:val="0"/>
        <w:displayVerticalDrawingGridEvery w:val="2"/>
        <w:characterSpacingControl w:val="compressPunctuation"/>
        <w:hdrShapeDefaults>
          <o:shapedefaults v:ext="edit" spidmax="18434"/>
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
          <w:adjustLineHeightInTable/>
          <w:useFELayout/>
        </w:compat>
        <w:rsids>
          <w:rsidRoot w:val="006C267C"/>
          <w:rsid w:val="00016A00"/>
          <w:rsid w:val="000C7874"/>
          <w:rsid w:val="000D585B"/>
          <w:rsid w:val="000E796D"/>
          <w:rsid w:val="00100826"/>
          <w:rsid w:val="00140F76"/>
          <w:rsid w:val="00166E26"/>
          <w:rsid w:val="00193FE7"/>
          <w:rsid w:val="0032343C"/>
          <w:rsid w:val="00362272"/>
          <w:rsid w:val="003A5B9E"/>
          <w:rsid w:val="00407EAB"/>
          <w:rsid w:val="00521A6D"/>
          <w:rsid w:val="00577F0B"/>
          <w:rsid w:val="005C0D74"/>
          <w:rsid w:val="00692987"/>
          <w:rsid w:val="006C267C"/>
          <w:rsid w:val="00995AE8"/>
          <w:rsid w:val="009C3512"/>
          <w:rsid w:val="00A239A1"/>
          <w:rsid w:val="00A358A7"/>
          <w:rsid w:val="00B417C6"/>
          <w:rsid w:val="00B961EE"/>
          <w:rsid w:val="00BE6E52"/>
          <w:rsid w:val="00C07FD9"/>
          <w:rsid w:val="00CA031B"/>
          <w:rsid w:val="00CF3F28"/>
          <w:rsid w:val="00D112A3"/>
          <w:rsid w:val="00D57817"/>
          <w:rsid w:val="00D97526"/>
          <w:rsid w:val="00DD1B3D"/>
        </w:rsids>
        <m:mathPr>
          <m:mathFont m:val="Cambria Math"/>
          <m:brkBin m:val="before"/>
          <m:brkBinSub m:val="--"/>
          <m:smallFrac/>
          <m:dispDef/>
          <m:lMargin m:val="0"/>
          <m:rMargin m:val="0"/>
          <m:defJc m:val="centerGroup"/>
          <m:wrapIndent m:val="1440"/>
          <m:intLim m:val="subSup"/>
          <m:naryLim m:val="undOvr"/>
        </m:mathPr>
        <w:themeFontLang w:val="en-US" w:eastAsia="zh-CN"/>
        <w:clrSchemeMapping w:bg1="light1" w:t1="dark1" w:bg2="light2" w:t2="dark2" w:accent1="accent1" w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5" w:accent6="accent6" w:hyperlink="hyperlink" w:followedHyperlink="followedHyperlink"/>
        <w:shapeDefaults>
          <o:shapedefaults v:ext="edit" spidmax="18434"/>
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
        <w:optimizeForBrowser/>
        <w:allowPNG/>
      </w:webSettings>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/docProps/core.xml" pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml" pkg:padding="256">
    <pkg:xmlData>
      <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <dc:creator>唐庆龙</dc:creator>
        <cp:lastModifiedBy>封刘柱</cp:lastModifiedBy>
        <cp:revision>2</cp:revision>
        <dcterms:created xsi:type="dcterms:W3CDTF">2017-09-05T06:54:00Z</dcterms:created>
        <dcterms:modified xsi:type="dcterms:W3CDTF">2017-09-05T06:54:00Z</dcterms:modified>
      </cp:coreProperties>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/styles.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
    <pkg:xmlData>
      <w:styles xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
        <w:docDefaults>
          <w:rPrDefault>
            <w:rPr>
              <w:rFonts w:asciiTheme="minorHAnsi" w:eastAsiaTheme="minorEastAsia" w:hAnsiTheme="minorHAnsi" w:cstheme="minorBidi"/>
              <w:kern w:val="2"/>
              <w:sz w:val="21"/>
              <w:szCs w:val="22"/>
              <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
            </w:rPr>
          </w:rPrDefault>
          <w:pPrDefault/>
        </w:docDefaults>
        <w:latentStyles w:defLockedState="0" w:defUIPriority="99" w:defSemiHidden="1" w:defUnhideWhenUsed="1" w:defQFormat="0" w:count="267">
          <w:lsdException w:name="Normal" w:semiHidden="0" w:uiPriority="0" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="heading 1" w:semiHidden="0" w:uiPriority="9" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="heading 2" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 3" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 4" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 5" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 6" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 7" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 8" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="heading 9" w:uiPriority="9" w:qFormat="1"/>
          <w:lsdException w:name="toc 1" w:uiPriority="39"/>
          <w:lsdException w:name="toc 2" w:uiPriority="39"/>
          <w:lsdException w:name="toc 3" w:uiPriority="39"/>
          <w:lsdException w:name="toc 4" w:uiPriority="39"/>
          <w:lsdException w:name="toc 5" w:uiPriority="39"/>
          <w:lsdException w:name="toc 6" w:uiPriority="39"/>
          <w:lsdException w:name="toc 7" w:uiPriority="39"/>
          <w:lsdException w:name="toc 8" w:uiPriority="39"/>
          <w:lsdException w:name="toc 9" w:uiPriority="39"/>
          <w:lsdException w:name="caption" w:uiPriority="35" w:qFormat="1"/>
          <w:lsdException w:name="Title" w:semiHidden="0" w:uiPriority="10" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Default Paragraph Font" w:uiPriority="1"/>
          <w:lsdException w:name="Subtitle" w:semiHidden="0" w:uiPriority="11" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Strong" w:semiHidden="0" w:uiPriority="22" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Emphasis" w:semiHidden="0" w:uiPriority="20" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Table Grid" w:semiHidden="0" w:uiPriority="39" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Placeholder Text" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="No Spacing" w:semiHidden="0" w:uiPriority="1" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Light Shading" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 1" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 1" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 1" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 1" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 1" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 1" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Revision" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="List Paragraph" w:semiHidden="0" w:uiPriority="34" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Quote" w:semiHidden="0" w:uiPriority="29" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Intense Quote" w:semiHidden="0" w:uiPriority="30" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Medium List 2 Accent 1" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 1" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 1" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 1" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 1" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 1" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 1" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 1" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 2" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 2" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 2" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 2" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 2" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 2" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2 Accent 2" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 2" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 2" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 2" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 2" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 2" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 2" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 2" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 3" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 3" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 3" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 3" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 3" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 3" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2 Accent 3" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 3" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 3" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 3" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 3" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 3" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 3" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 3" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 4" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 4" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 4" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 4" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 4" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 4" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2 Accent 4" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 4" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 4" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 4" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 4" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 4" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 4" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 4" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 5" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 5" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 5" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 5" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 5" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 5" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2 Accent 5" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 5" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 5" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 5" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 5" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 5" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 5" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 5" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Shading Accent 6" w:semiHidden="0" w:uiPriority="60" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light List Accent 6" w:semiHidden="0" w:uiPriority="61" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Light Grid Accent 6" w:semiHidden="0" w:uiPriority="62" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 1 Accent 6" w:semiHidden="0" w:uiPriority="63" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Shading 2 Accent 6" w:semiHidden="0" w:uiPriority="64" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 1 Accent 6" w:semiHidden="0" w:uiPriority="65" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium List 2 Accent 6" w:semiHidden="0" w:uiPriority="66" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 1 Accent 6" w:semiHidden="0" w:uiPriority="67" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 2 Accent 6" w:semiHidden="0" w:uiPriority="68" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Medium Grid 3 Accent 6" w:semiHidden="0" w:uiPriority="69" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Dark List Accent 6" w:semiHidden="0" w:uiPriority="70" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Shading Accent 6" w:semiHidden="0" w:uiPriority="71" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful List Accent 6" w:semiHidden="0" w:uiPriority="72" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Colorful Grid Accent 6" w:semiHidden="0" w:uiPriority="73" w:unhideWhenUsed="0"/>
          <w:lsdException w:name="Subtle Emphasis" w:semiHidden="0" w:uiPriority="19" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Intense Emphasis" w:semiHidden="0" w:uiPriority="21" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Subtle Reference" w:semiHidden="0" w:uiPriority="31" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Intense Reference" w:semiHidden="0" w:uiPriority="32" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Book Title" w:semiHidden="0" w:uiPriority="33" w:unhideWhenUsed="0" w:qFormat="1"/>
          <w:lsdException w:name="Bibliography" w:uiPriority="37"/>
          <w:lsdException w:name="TOC Heading" w:uiPriority="39" w:qFormat="1"/>
        </w:latentStyles>
        <w:style w:type="paragraph" w:default="1" w:styleId="a">
          <w:name w:val="Normal"/>
          <w:qFormat/>
          <w:rsid w:val="00CA031B"/>
          <w:pPr>
            <w:widowControl w:val="0"/>
            <w:jc w:val="both"/>
          </w:pPr>
        </w:style>
        <w:style w:type="character" w:default="1" w:styleId="a0">
          <w:name w:val="Default Paragraph Font"/>
          <w:uiPriority w:val="1"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
        </w:style>
        <w:style w:type="table" w:default="1" w:styleId="a1">
          <w:name w:val="Normal Table"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
          <w:qFormat/>
          <w:tblPr>
            <w:tblInd w:w="0" w:type="dxa"/>
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
          <w:link w:val="Char"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
          <w:rsid w:val="00362272"/>
          <w:pPr>
            <w:pBdr>
              <w:bottom w:val="single" w:sz="6" w:space="1" w:color="auto"/>
            </w:pBdr>
            <w:tabs>
              <w:tab w:val="center" w:pos="4153"/>
              <w:tab w:val="right" w:pos="8306"/>
            </w:tabs>
            <w:snapToGrid w:val="0"/>
            <w:jc w:val="center"/>
          </w:pPr>
          <w:rPr>
            <w:sz w:val="18"/>
            <w:szCs w:val="18"/>
          </w:rPr>
        </w:style>
        <w:style w:type="character" w:customStyle="1" w:styleId="Char">
          <w:name w:val="页眉 Char"/>
          <w:basedOn w:val="a0"/>
          <w:link w:val="a3"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:rsid w:val="00362272"/>
          <w:rPr>
            <w:sz w:val="18"/>
            <w:szCs w:val="18"/>
          </w:rPr>
        </w:style>
        <w:style w:type="paragraph" w:styleId="a4">
          <w:name w:val="footer"/>
          <w:basedOn w:val="a"/>
          <w:link w:val="Char0"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:unhideWhenUsed/>
          <w:rsid w:val="00362272"/>
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
            <w:szCs w:val="18"/>
          </w:rPr>
        </w:style>
        <w:style w:type="character" w:customStyle="1" w:styleId="Char0">
          <w:name w:val="页脚 Char"/>
          <w:basedOn w:val="a0"/>
          <w:link w:val="a4"/>
          <w:uiPriority w:val="99"/>
          <w:semiHidden/>
          <w:rsid w:val="00362272"/>
          <w:rPr>
            <w:sz w:val="18"/>
            <w:szCs w:val="18"/>
          </w:rPr>
        </w:style>
      </w:styles>
    </pkg:xmlData>
  </pkg:part>
  <pkg:part pkg:name="/word/fontTable.xml" pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
    <pkg:xmlData>
      <w:fonts xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
        <w:font w:name="Calibri">
          <w:panose1 w:val="020F0502020204030204"/>
          <w:charset w:val="00"/>
          <w:family w:val="swiss"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="宋体">
          <w:altName w:val="SimSun"/>
          <w:panose1 w:val="02010600030101010101"/>
          <w:charset w:val="86"/>
          <w:family w:val="auto"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="Times New Roman">
          <w:panose1 w:val="02020603050405020304"/>
          <w:charset w:val="00"/>
          <w:family w:val="roman"/>
          <w:pitch w:val="variable"/>
          <w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="幼圆">
          <w:panose1 w:val="02010509060101010101"/>
          <w:charset w:val="86"/>
          <w:family w:val="modern"/>
          <w:pitch w:val="fixed"/>
          <w:sig w:usb0="00000001" w:usb1="080E0000" w:usb2="00000010" w:usb3="00000000" w:csb0="00040000" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="楷体_GB2312">
          <w:altName w:val="楷体"/>
          <w:charset w:val="86"/>
          <w:family w:val="modern"/>
          <w:pitch w:val="fixed"/>
          <w:sig w:usb0="00000000" w:usb1="080E0000" w:usb2="00000010" w:usb3="00000000" w:csb0="00040000" w:csb1="00000000"/>
        </w:font>
        <w:font w:name="Calibri Light">
          <w:panose1 w:val="020F0302020204030204"/>
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
        <Words>46</Words>
        <Characters>267</Characters>
        <Application>Microsoft Office Word</Application>
        <DocSecurity>0</DocSecurity>
        <Lines>2</Lines>
        <Paragraphs>1</Paragraphs>
        <ScaleCrop>false</ScaleCrop>
        <Company>Microsoft</Company>
        <LinksUpToDate>false</LinksUpToDate>
        <CharactersWithSpaces>312</CharactersWithSpaces>
        <SharedDoc>false</SharedDoc>
        <HyperlinksChanged>false</HyperlinksChanged>
        <AppVersion>12.0000</AppVersion>
      </Properties>
    </pkg:xmlData>
  </pkg:part>
</pkg:package>
