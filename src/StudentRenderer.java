import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class StudentRenderer implements ListCellRenderer {

	private final JLabel jlblCell = new JLabel(" ", JLabel.LEFT);
    Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        jlblCell.setOpaque(true);

        if (isSelected) {
            jlblCell.setForeground(jList.getSelectionForeground());
            jlblCell.setBackground(jList.getSelectionBackground());
            jlblCell.setBorder(new LineBorder(Color.BLUE));
        } else {
            jlblCell.setForeground(jList.getForeground());
            jlblCell.setBackground(jList.getBackground());
        }

        jlblCell.setBorder(cellHasFocus ? lineBorder : emptyBorder);

        return jlblCell;
    }

	

}
