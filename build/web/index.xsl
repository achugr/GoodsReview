<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template name="main">
        <xsl:apply-templates select="page/data" mode="show"/>
    </xsl:template>


    <xsl:template match="data" mode="show">
        <xsl:for-each select="brand">
           <a>
              <xsl:attribute name="href">showbrand.xml?id=<xsl:value-of select="@id"/></xsl:attribute>
              <xsl:value-of select="name"/><br/>
           </a>
              
          <!--  <strong><xsl:value-of select="about"/></strong><br/> -->
        </xsl:for-each>
    </xsl:template>
 
</xsl:stylesheet>
