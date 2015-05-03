/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;




import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class TextAreaCell extends AbstractCellEditor
                         implements TableCellEditor, KeyListener {
    
    
    
    private  JTextArea textArea ;
    private  JScrollPane scrollPane;
    private JTable table;

    public TextAreaCell(JTable table) {
        // Recibo la tabla. 
        this.table = table;
        
        
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(3);
        textArea.addKeyListener(this);
        
        // Scroll del TextArea
        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                textArea.requestFocusInWindow();
                textArea.selectAll();
            }
        });

    }

  
    

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {
        JLabel labOut = new JLabel("<html><body><pre> <p style='width: 300px;'>"+textArea.getText().replaceAll("\n", "<br>")+"</p></pre></body></html>");
        
        return labOut.getText();
        
    }

    
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        
        
        return scrollPane;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
       
    }

    @Override
    public void keyPressed(KeyEvent ke) {
         // tab key will transfer focus to next cell
        if ( ke.getKeyCode() == KeyEvent.VK_TAB &&  !ke.isShiftDown()) {
            
            ke.consume();

            int column = table.getEditingColumn();
            int row = table.getEditingRow();
            
            
            if ((column + 1)  >= table.getColumnCount()) {
                // if column is last column, check if there is next row
                if ((row + 1) >= table.getRowCount()) row = -1;
                else {
                    row++;
                    column = 0;
                }

            } else column++;

            if (row > -1 & column > -1)
                
                table.changeSelection(0, column, false, false);

            
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    
}

