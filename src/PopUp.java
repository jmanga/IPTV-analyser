import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JList;

public class PopUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPPopUp = null;
	private JLabel jLPopUp = null;
	static JLabel[] jLErros = new JLabel[30];
	private JToggleButton jTBPopUp = null;
	/**
	 * This is the default constructor
	 */
	public PopUp() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(327, 378);
		this.setContentPane(getJPPopUp());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jPPopUp	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPPopUp() {
		if (jPPopUp == null) {
			int k = 35;
			for(int i = 0; i < jLErros.length; i++){
				jLErros[i] = new JLabel();
				jLErros[i].setBounds(new Rectangle(14, k, 284, 211));
				jLErros[i].setForeground(Color.white);
				jLErros[i].setText("");
				jLErros[i].setVerticalAlignment(SwingConstants.TOP);
				jLErros[i].setHorizontalAlignment(SwingConstants.CENTER);
				jLErros[i].setFont(new Font("Dialog", Font.BOLD, 12));
				k += 15;
			}

			jLPopUp = new JLabel();
			jLPopUp.setBounds(new Rectangle(14, 7, 358, 16));
			jLPopUp.setFont(new Font("Dialog", Font.BOLD, 14));
			jLPopUp.setForeground(Color.white);
			jLPopUp.setText("Parâmetros Incorretos:");
			jPPopUp = new JPanel();
			jPPopUp.setBackground(new Color(84, 128, 188));
			jPPopUp.setLayout(null);
			jPPopUp.add(jLPopUp, null);
			for(int i = 0; i < jLErros.length; i++)
				jPPopUp.add(jLErros[i], null);
			jPPopUp.add(getJTBPopUp(), null);
		}
		return jPPopUp;
	}

	/**
	 * This method initializes jTBPopUp	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJTBPopUp() {
		if (jTBPopUp == null) {
			jTBPopUp = new JToggleButton();
			jTBPopUp.setBounds(new Rectangle(119, 308, 74, 22));
			jTBPopUp.setText("OK");
			jTBPopUp.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					setVisible(false);
					jTBPopUp.setSelected(true);
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		return jTBPopUp;
	}

}  //  @jve:decl-index=0:visual-constraint="137,-1"
