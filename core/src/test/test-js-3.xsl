<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="1.0">
    <xsl:output method="xml" indent="no" encoding="utf-8"/>
    <xsl:template match="/">
        <root>
        <xsl:copy-of select="."/>
        <test>
            <xsl:value-of select="count(//*)"/>
        </test>
        </root>
    </xsl:template>
</xsl:stylesheet>