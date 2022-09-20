<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:template match="/">
      <html>
         <body>
            <h1>Biblioteca Municipal</h1>
            <table border="1">
               <tr>
                  <th>Libro</th>
                  <th>Autor</th>
                  <th>ISBN</th>
		  <th>Editorial</th>
		  <th>Ano</th>
		  <th>Paginas</th>
               </tr>
               <xsl:for-each select="Biblioteca/Libro">
                  <tr>
                     <td>
                        <xsl:value-of select="Titulo" />
                     </td>
                      <td>
                        <xsl:value-of select="Autor" />
                     </td>
                       <td>
                        <xsl:value-of select="ISBN" />
                     </td>
                       <td>
                        <xsl:value-of select="Editorial" />
                     </td>
                       <td>
                        <xsl:value-of select="Ano" />
                     </td>
                       <td>
                        <xsl:value-of select="Paginas" />
                     </td>
                  </tr>
               </xsl:for-each>
            </table>
         </body>
      </html>
   </xsl:template>
</xsl:stylesheet>