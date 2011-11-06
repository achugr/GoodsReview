<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="common.xsl"/>
    <xsl:include href="common-view.xsl"/>

    <xsl:template match="/">
        <xsl:call-template name="test"/>
    </xsl:template>

    <xsl:template  name="main">
        <h3>Результаты поиска по запросу "<xsl:value-of select="query"/>"</h3>
        <ul class="searchResult">
            <xsl:for-each select="//detailed-product-for-view">
                <xsl:call-template name="productNew"/>
            </xsl:for-each>
        </ul>
        <ul class="popularProducts">
            <xsl:call-template name="popular"/>
        </ul>
    </xsl:template>
</xsl:stylesheet>
