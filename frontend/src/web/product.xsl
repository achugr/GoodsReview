<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:call-template name="title"/>
            </head>
            <body>
                <xsl:call-template name="logo"/>
                <xsl:call-template name="greeting"/>
                <tr>
                    <xsl:call-template name="find"/>
                </tr>
                <xsl:apply-templates/>
                <hr/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="//product-for-view">
        <xsl:call-template name="product"/>
    </xsl:template>

    <xsl:template match="data[@id='thesis']">
        <h2> Theses:</h2>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//thesis-for-view">
        <xsl:call-template name="thesis"/>
        <br/>
    </xsl:template>

</xsl:stylesheet>

