/*
 * Clase para que se mire mejor el Text Area. 
 */
package virtualdocumentslibrary;






import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class TextAreaCellView extends DefaultTableCellRenderer
{
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;

    public TextAreaCellView() {
       
    }
    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column)
        {
                if(value instanceof JTextArea)
                {
                    JScrollPane scrollPane;
                    JTextArea textArea = (JTextArea) value;
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setRows(3);
                    

                    // Scroll del TextArea
                    scrollPane = new JScrollPane(textArea);
                    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    
                    
                    
                    return textArea;
                }
                else
                {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
        }


   

   
  
}
