<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="template">
        <html>
            <head>
                <title>
                    <xsl:text>Goods Review</xsl:text>
                </title>
            </head>
            <body>
                <h3>Goods Review</h3>
                <tr>
                    <xsl:call-template name="find"/>
                </tr>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <tr align="center">
            <td colspan="3">
                <form method="get" action="/search.xml">
                    <input type="text" name="query" size="100"/>
                    <input type="submit" value="Find"/>
                </form>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="title">
        <title>
            <xsl:text>Goods Review</xsl:text>
        </title>
    </xsl:template>
</xsl:stylesheet>