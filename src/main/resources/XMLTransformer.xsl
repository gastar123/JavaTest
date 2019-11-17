<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/entries">
        <entries>
            <xsl:apply-templates select="entry"/>
        </entries>
    </xsl:template>

    <xsl:template match="entry">
        <entry>
            <xsl:attribute name="field">
                <xsl:value-of select="field"/>
            </xsl:attribute>
        </entry>
    </xsl:template>

</xsl:stylesheet>