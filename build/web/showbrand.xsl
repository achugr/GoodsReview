<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:include href="common.xsl"/>

    <!--<xsl:key name="attraction-item" match="/page/data/basket/content/basket-market-item" use="@uid"/>-->


    <xsl:template name="main">
        <xsl:apply-templates select="page/data/collection" mode="show"/>
    </xsl:template>


    <xsl:template match="collection" mode="show">
        <xsl:for-each select="brand">
           <h2>
              <xsl:value-of select="name"/><br/>
           </h2>
           <a>
	      <xsl:value-of select="about"/><br/>
           </a>
            <xsl:for-each select="graph">
                <canvas id="example" width="400" height="200"></canvas>
                <script type="text/javascript">
                var g = new Bluff.Line('example', '400x200');
                g.title = "<xsl:value-of select="description"/>";
                g.tooltips = true;

                g.theme_pastel();
                g.hide_legend = 'true';
                g.data("data", <xsl:value-of select="dots"/>);

                g.labels = {0: 'октябрь 2011', 2: 'ноябрь 2011', 4: 'декабрь 2011'};
                g.draw();
                </script>
            </xsl:for-each>
          <!--  <strong><xsl:value-of select="about"/></strong><br/> -->
        </xsl:for-each>
    </xsl:template>
 
</xsl:stylesheet>
