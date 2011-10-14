<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:key match="/page/items/item" name="item" use="@uid"/>

    <xsl:template match="/">
        <html>
        <head>
            <title><xsl:text>BrandAnalyst</xsl:text></title>
            <link type="text/css" rel="stylesheet" href="basket.css"/>
            <script language="javascript" src="javascripts/js-class.js" type="text/javascript"></script><script language="javascript" src="javascripts/bluff.js" type="text/javascript"></script><script language="javascript" src="javascripts/excanvas.js" type="text/javascript"></script>
        </head>
        <body>
            <xsl:call-template name="find"/>
            <xsl:call-template name="main"/>
        </body>
        </html>
    </xsl:template>

    <xsl:template name="find">
        <table width="50%" align="center">
            <tr>
                <form method="get" action="/search.xml">
                    <input type="text" name="query" size="30"/>
                    <input type="submit" value="побрендить"/>
                </form>
            </tr>
        </table>
    </xsl:template>

</xsl:stylesheet>