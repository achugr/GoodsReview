<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:call-template name="title"/>
                <link type="text/css" rel="stylesheet" href="main.css"/>
            </head>
            <body>
                <img id="logo" src="goodsReview.png" width="150" height="150"/>
                <div id ="content">
                    <xsl:call-template name="greeting"/>
                    <tr>
                        <xsl:call-template name="find"/>
                    </tr>
                    <xsl:call-template name="popular"/>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
