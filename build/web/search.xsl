<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <!--<xsl:key name="attraction-item" match="/page/data/basket/content/basket-market-item" use="@uid"/>-->


    <xsl:template name="main">
        <xsl:apply-templates select="page/data" mode="show"/>
    </xsl:template>


    <xsl:template match="data" mode="show">
        <h2>
            <xsl:value-of select="answer"/>
        </h2>
    </xsl:template>



</xsl:stylesheet>
