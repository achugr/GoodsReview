<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="1.0">
    <xsl:output method="html" indent="no" encoding="utf-8"/>
    <xsl:template match="/">
        <html>
            <xsl:for-each select="page/data">
                <xsl:value-of select="string"/>
            </xsl:for-each>
        </html>
    </xsl:template>
</xsl:stylesheet>