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
                <h3>Goods Review</h3>
                <tr>
                    <xsl:call-template name="find"/>
                </tr>
                <xsl:apply-templates/>
                <hr />
            </body>
        </html>
    </xsl:template>

    <xsl:template match="product-for-view">
        <hr />
        Name:
        <xsl:value-of select="name"/><br/>
        Description:
        <xsl:value-of select="description"/><br/>
    </xsl:template>


</xsl:stylesheet>
