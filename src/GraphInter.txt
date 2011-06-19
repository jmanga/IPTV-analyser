import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;


public class GraphInter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jL_Titulo = null;
	private JToggleButton jOK = null;
	private JToggleButton jCancela = null;
	private JToggleButton jLimpar = null;
	private JTextField jTFCriterioParada = null;
	private JLabel jLCriterioParada = null;
	private JLabel jLCriterioParadaOptions = null;
	private JComboBox jCBCriterioParadaOptions = null;
	private JLabel jLCorrelIP = null;
	private JLabel jLDesvPadP = null;
	private JLabel jLDesvPadI = null;
	private JLabel jLCorrelPB = null;
	private JTextField jTFCorrelIP = null;
	

	int CriterioParada = 0;
	double CorrelIP = 0;
	double CorrelPB = 0;
	double DesvPadP = 0;
	double DesvPadI = 0;
	double DesvPadB = 0;
	int TamPacote = 0;
	double MediaChegadaSessoes = 0;
	double MediaTamanhoSessoes = 0;
	double PacotesFixo = 0;
	int TipoGOP1 = 0;
	int TipoGOP2 = 0;
	double ParamDist1 = 0;
	double ParamDist2 = 0;
	double ParamDistTam = 0;
	double ParamDistTam2 = 0;
	double ParamDist1Pac1 = 0;
	double ParamDist1Pac2 = 0;
	double ErroP = 0;
	double ErroB = 0;
	String NomeArquivo = null;
	double[] RetChegSes = new double[3];
	double[] RetTamSes = new double[3];
	double[] RetChegPac = new double[3];
	

	private JTextField jTFCorrelPB = null;
	private JTextField jTFDesvPadP = null;
	private JTextField jTFDesvPadI = null;
	private JLabel jLDesvPadB = null;
	private JTextField jTFDesvPadB = null;
	private JLabel jLTamPacote = null;
	private JTextField jTFTamPacote = null;
	private JLabel jLMediaChegadaSessoes = null;
	private JTextField jTFMediaChegadaSessoes = null;
	private JLabel jLMediaTamanhoSessoes = null;
	private JTextField jTFMediaTamanhoSessoes = null;
	private JLabel jLTempoPacotes = null;
	private JRadioButton jRBPacotesFixo = null;
	private JRadioButton jRBPacotesDistribuicao = null;
	private JTextField jTFPacotesFixo = null;
	private JComboBox jCBPacotesDistribuicao = null;
	private JLabel jLTipoGOP = null;
	private JTextField jTFTipoGOP1 = null;
	private JTextField jTFTipoGOP2 = null;
	private JRadioButton jRBSessoesFixo = null;
	private JRadioButton jRBSessoesDistribuicao = null;
	private JTextField jTFParamDist1 = null;
	private JComboBox jCBSessoesDistribuicao = null;
	private JLabel jLParamSessoes = null;
	private JTextField jTFParamDist2 = null;
	private JRadioButton jRBSessoesFixo2 = null;
	private JRadioButton jRBSessoesDistribuicao2 = null;
	private JLabel jLParamSessoesTam = null;
	private JTextField jTFParamDistTam = null;
	private JTextField jTFParamDistTam2 = null;
	private JComboBox jCBSessoesDistribuicaoTam = null;
	
	String[] Distribuicao = {"Distribuicao Exponencial", "Distribuicao Gamma"};
	String ParErrados;
	boolean openPopUp = false;
	PopUp popup = new PopUp();
	int pos = 0;
	
	private JLabel jLValorTamanhoSessoes = null;
	private JLabel jLValoresChegadaSessoes = null;
	private JLabel jLValorChegadaPacotes = null;
	private JLabel jLMediaChegadaPacotes = null;
	private JTextField jTFParamDist1Pac1 = null;
	private JTextField jTFParamDist1Pac2 = null;
	private JLabel jLNomeArquivo = null;
	private JTextField jTFNomeArquivo = null;
	private JLabel jLErro = null;
	private JTextField jTFErroP = null;
	private JLabel jLErroP = null;
	private JTextField jTFErroB = null;
	private JLabel jLErroB = null;
	private JToggleButton jValoresDefault = null;
	
	/**
	 * This is the default constructor
	 */
	public GraphInter() {
		super();
		initialize();
		ButtonGroup();
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(864, 657);
		this.setContentPane(getJPanel());
		this.setTitle("JFrame");
		
		jRBPacotesDistribuicao.setSelected(true);
		jTFPacotesFixo.setVisible(false);
    	jCBPacotesDistribuicao.setVisible(true);
    	jTFPacotesFixo.setVisible(false);
    	jTFParamDist1Pac2.setVisible(false);
    	
    	jRBSessoesDistribuicao.setSelected(true);
		jRBSessoesFixo.setSelected(false);
		jCBSessoesDistribuicao.setVisible(true);
		jTFMediaChegadaSessoes.setVisible(false);
		jTFParamDist2.setVisible(false);
		
		jRBSessoesDistribuicao2.setSelected(true);
		jRBSessoesFixo2.setSelected(false);
		jCBSessoesDistribuicaoTam.setVisible(true);
		jTFMediaTamanhoSessoes.setVisible(false);
		jTFParamDistTam2.setVisible(false);
		
	}
	
	private void ButtonGroup(){
		ButtonGroup group1 = new ButtonGroup();
		ButtonGroup group2 = new ButtonGroup();
		ButtonGroup group3 = new ButtonGroup();
		
		group1.add(jRBPacotesDistribuicao);
		group1.add(jRBPacotesFixo);
		
		group2.add(jRBSessoesDistribuicao);
		group2.add(jRBSessoesFixo);
		
		group3.add(jRBSessoesDistribuicao2);
		group3.add(jRBSessoesFixo2);
		
		jRBPacotesDistribuicao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFPacotesFixo.setVisible(false);
            	jCBPacotesDistribuicao.setVisible(true);
            	jLValorChegadaPacotes.setVisible(true);
            	jTFParamDist1Pac1.setVisible(true);
            	jTFParamDist1Pac2.setVisible(true);
            	if(jCBPacotesDistribuicao.getSelectedIndex() == 0){
					jTFParamDist1Pac2.setVisible(false);
					RetChegPac[0] = 0;
				}
				else if(jCBPacotesDistribuicao.getSelectedIndex() == 1){
					jTFParamDist1Pac2.setVisible(true);
					RetChegPac[0] = 2;
				}

            }
        });
		
		jRBPacotesFixo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFPacotesFixo.setVisible(true);
            	jCBPacotesDistribuicao.setVisible(false);
            	jLValorChegadaPacotes.setVisible(false);
            	jTFParamDist1Pac1.setVisible(false);
            	jTFParamDist1Pac2.setVisible(false);
            	RetChegPac[0] = 1;
            	
            }
        });
		
		jRBSessoesDistribuicao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFMediaChegadaSessoes.setVisible(false);
            	jCBSessoesDistribuicao.setVisible(true);
            	jLParamSessoes.setVisible(true);
            	jTFParamDist1.setVisible(true);
            	jTFParamDist2.setVisible(true);
            	if(jCBSessoesDistribuicao.getSelectedIndex() == 0){
					jTFParamDist2.setVisible(false);
					RetChegSes[0] = 0;
				}
				else if(jCBSessoesDistribuicao.getSelectedIndex() == 1){
					jTFParamDist2.setVisible(true);
					RetChegSes[0] = 2;
				}
            }
        });
		
		jRBSessoesFixo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFMediaChegadaSessoes.setVisible(true);
            	jCBSessoesDistribuicao.setVisible(false);
            	jLParamSessoes.setVisible(false);
            	jTFParamDist1.setVisible(false);
            	jTFParamDist2.setVisible(false);
            	RetChegSes[0] = 1;
            }
        });
		jRBSessoesDistribuicao2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFMediaTamanhoSessoes.setVisible(false);
            	jCBSessoesDistribuicaoTam.setVisible(true);
            	jLParamSessoesTam.setVisible(true);
            	jTFParamDistTam.setVisible(true);
            	jTFParamDistTam2.setVisible(true);
            	if(jCBSessoesDistribuicaoTam.getSelectedIndex() == 0){
					jTFParamDistTam2.setVisible(false);
					RetTamSes[0] = 0;
				}
				else if(jCBSessoesDistribuicaoTam.getSelectedIndex() == 1){
					jTFParamDistTam2.setVisible(true);
					RetTamSes[0] = 2;
				}
            }
        });
		
		jRBSessoesFixo2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	jTFMediaTamanhoSessoes.setVisible(true);
            	jCBSessoesDistribuicaoTam.setVisible(false);
            	jLParamSessoesTam.setVisible(false);
            	jTFParamDistTam.setVisible(false);
            	jTFParamDistTam2.setVisible(false);
            	RetTamSes[0] = 1;
            }
        });
		
		jCBPacotesDistribuicao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(jCBPacotesDistribuicao.getSelectedIndex() == 0){
					jTFParamDist1Pac2.setVisible(false);
				}
				else if(jCBPacotesDistribuicao.getSelectedIndex() == 1)
					jTFParamDist1Pac2.setVisible(true);
			}
		});
		jCBSessoesDistribuicao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(jCBSessoesDistribuicao.getSelectedIndex() == 0){
					jTFParamDist2.setVisible(false);
				}
				else if(jCBSessoesDistribuicao.getSelectedIndex() == 1)
					jTFParamDist2.setVisible(true);
			}
		});
		jCBSessoesDistribuicaoTam.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(jCBSessoesDistribuicaoTam.getSelectedIndex() == 0){
					jTFParamDistTam2.setVisible(false);
				}
				else if(jCBSessoesDistribuicaoTam.getSelectedIndex() == 1)
					jTFParamDistTam2.setVisible(true);
			}
		});
 
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLErroB = new JLabel();
			jLErroB.setBounds(new Rectangle(161, 490, 64, 15));
			jLErroB.setText("Erro de B");
			jLErroP = new JLabel();
			jLErroP.setBounds(new Rectangle(63, 490, 57, 16));
			jLErroP.setText("Erro de P");
			jLErro = new JLabel();
			jLErro.setBounds(new Rectangle(42, 462, 110, 16));
			jLErro.setText("Erros");
			jLNomeArquivo = new JLabel();
			jLNomeArquivo.setBounds(new Rectangle(637, 413, 195, 23));
			jLNomeArquivo.setText("Nome do Arquivo");
			jLMediaChegadaPacotes = new JLabel();
			jLMediaChegadaPacotes.setBounds(new Rectangle(623, 133, 197, 16));
			jLMediaChegadaPacotes.setText("Valor Fixo / Tipo de Distribuição");
			jLValorChegadaPacotes = new JLabel();
			jLValorChegadaPacotes.setBounds(new Rectangle(630, 210, 164, 22));
			jLValorChegadaPacotes.setText("Parâmetro(s) da Distribuição");
			jLValoresChegadaSessoes = new JLabel();
			jLValoresChegadaSessoes.setBounds(new Rectangle(336, 98, 211, 22));
			jLValoresChegadaSessoes.setText("Chegada das Sessões");
			jLValorTamanhoSessoes = new JLabel();
			jLValorTamanhoSessoes.setBounds(new Rectangle(350, 329, 204, 16));
			jLValorTamanhoSessoes.setText("Valor Fixo / Tipo de Distribuição");
			jLParamSessoesTam = new JLabel();
			jLParamSessoesTam.setBounds(new Rectangle(350, 406, 164, 22));
			jLParamSessoesTam.setText("Parâmetro(s) da Distribuição");
			jLParamSessoes = new JLabel();
			jLParamSessoes.setBounds(new Rectangle(350, 210, 174, 22));
			jLParamSessoes.setText("Parâmetro(s) da Distribuição");
			jLTipoGOP = new JLabel();
			jLTipoGOP.setBounds(new Rectangle(609, 329, 186, 22));
			jLTipoGOP.setText("Tipo de GOP (ex.: [12][3])");
			jLTempoPacotes = new JLabel();
			jLTempoPacotes.setBounds(new Rectangle(609, 98, 219, 22));
			jLTempoPacotes.setText("Intervalo de Chegada de Pacotes");
			jLMediaTamanhoSessoes = new JLabel();
			jLMediaTamanhoSessoes.setBounds(new Rectangle(336, 301, 218, 21));
			jLMediaTamanhoSessoes.setText("Tamanho das Sessões");
			jLMediaChegadaSessoes = new JLabel();
			jLMediaChegadaSessoes.setBounds(new Rectangle(350, 133, 219, 17));
			jLMediaChegadaSessoes.setText("Valor Fixo / Tipo de Distribuição");
			jLTamPacote = new JLabel();
			jLTamPacote.setBounds(new Rectangle(609, 280, 218, 18));
			jLTamPacote.setText("MTU");
			jLDesvPadB = new JLabel();
			jLDesvPadB.setBounds(new Rectangle(42, 413, 110, 16));
			jLDesvPadB.setText("Desvio Padrão de B");
			jLCorrelPB = new JLabel();
			jLCorrelPB.setBounds(new Rectangle(42, 266, 162, 16));
			jLCorrelPB.setText("Correlação entre P e B");
			jLDesvPadI = new JLabel();
			jLDesvPadI.setBounds(new Rectangle(42, 315, 156, 16));
			jLDesvPadI.setText("Desvio Padrão de I");
			jLDesvPadP = new JLabel();
			jLDesvPadP.setBounds(new Rectangle(42, 364, 153, 16));
			jLDesvPadP.setText("Desvio Padrão de P");
			jLCorrelIP = new JLabel();
			jLCorrelIP.setBounds(new Rectangle(42, 217, 153, 16));
			jLCorrelIP.setText("Correlação entre I e P");
			jLCriterioParadaOptions = new JLabel();
			jLCriterioParadaOptions.setBounds(new Rectangle(42, 98, 204, 23));
			jLCriterioParadaOptions.setText("Critério de Parada");
			jLCriterioParada = new JLabel();
			jLCriterioParada.setBounds(new Rectangle(42, 154, 239, 22));
			jLCriterioParada.setText("Valor do Critério de Parada (Quantidade)");
			jL_Titulo = new JLabel();
			jL_Titulo.setBounds(new Rectangle(97, 14, 680, 50));
			jL_Titulo.setFont(new Font("Nyala", Font.BOLD, 48));
			jL_Titulo.setBackground(new Color(26, 87, 115));
			jL_Titulo.setText("Simulador de Tráfego de IPTV");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(new Color(173, 211, 254));
			jPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel.add(jL_Titulo, null);
			jPanel.add(getJOK(), null);
			jPanel.add(getJCancela(), null);
			jPanel.add(getJLimpar(), null);
			jPanel.add(getJTFCriterioParada(), null);
			jPanel.add(jLCriterioParada, null);
			jPanel.add(jLCriterioParadaOptions, null);
			jPanel.add(getJCBCriterioParadaOptions(), null);
			jPanel.add(jLCorrelIP, null);
			jPanel.add(jLDesvPadP, null);
			jPanel.add(jLDesvPadI, null);
			jPanel.add(jLCorrelPB, null);
			jPanel.add(getJTFCorrelIP(), null);
			jPanel.add(getJTFCorrelPB(), null);
			jPanel.add(getJTFDesvPadP(), null);
			jPanel.add(getJTFDesvPadI(), null);
			jPanel.add(jLDesvPadB, null);
			jPanel.add(getJTFDesvPadB(), null);
			jPanel.add(jLTamPacote, null);
			jPanel.add(getJTFTamPacote(), null);
			jPanel.add(jLMediaChegadaSessoes, null);
			jPanel.add(getJTFMediaChegadaSessoes(), null);
			jPanel.add(jLMediaTamanhoSessoes, null);
			jPanel.add(getJTFMediaTamanhoSessoes(), null);
			jPanel.add(jLTempoPacotes, null);
			jPanel.add(getJRBPacotesFixo(), null);
			jPanel.add(getJRBPacotesDistribuicao(), null);
			jPanel.add(getJTFPacotesFixo(), null);
			jPanel.add(getJCBPacotesDistribuicao(), null);
			jPanel.add(jLTipoGOP, null);
			jPanel.add(getJTFTipoGOP1(), null);
			jPanel.add(getJTFTipoGOP2(), null);
			jPanel.add(getJRBSessoesFixo(), null);
			jPanel.add(getJRBSessoesDistribuicao(), null);
			jPanel.add(getJTFParamDist1(), null);
			jPanel.add(getJCBSessoesDistribuicao(), null);
			jPanel.add(jLParamSessoes, null);
			jPanel.add(getJTFParamDist2(), null);
			jPanel.add(getJRBSessoesFixo2(), null);
			jPanel.add(getJRBSessoesDistribuicao2(), null);
			jPanel.add(jLParamSessoesTam, null);
			jPanel.add(getJTFParamDistTam(), null);
			jPanel.add(getJTFParamDistTam2(), null);
			jPanel.add(getJCBSessoesDistribuicaoTam(), null);
			jPanel.add(jLValorTamanhoSessoes, null);
			jPanel.add(jLValoresChegadaSessoes, null);
			jPanel.add(jLValorChegadaPacotes, null);
			jPanel.add(jLMediaChegadaPacotes, null);
			jPanel.add(getJTFParamDist1Pac1(), null);
			jPanel.add(getJTFParamDist1Pac2(), null);
			jPanel.add(jLNomeArquivo, null);
			jPanel.add(getJTFNomeArquivo(), null);
			jPanel.add(jLErro, null);
			jPanel.add(getJTFErroP(), null);
			jPanel.add(jLErroP, null);
			jPanel.add(getJTFErroB(), null);
			jPanel.add(jLErroB, null);
			jPanel.add(getJValoresDefault(), null);
		}
		return jPanel;
	}


	/**
	 * This method initializes jOK	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJOK() {
		if (jOK == null) {
			jOK = new JToggleButton();
			jOK.setBounds(new Rectangle(210, 567, 106, 22));
			jOK.setText("OK");
			jOK.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					openPopUp = false;
					pos = 0;
					for(int i = 0; i<popup.jLErros.length; i++)
						popup.jLErros[i].setText("");
					check();
					jOK.setSelected(true);
					if(openPopUp == true){
						popup.setVisible(true);
					}
					if(openPopUp == false){
						Simulator MainThread =  new Simulator();
						new Thread(MainThread).start();
					}
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		
		return jOK;
	}


	/**
	 * This method initializes jCancela	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJCancela() {
		if (jCancela == null) {
			jCancela = new JToggleButton();
			jCancela.setBounds(new Rectangle(360, 567, 106, 22));
			jCancela.setText("Fechar");
			jCancela.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseExited(java.awt.event.MouseEvent e) {

				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					jCancela.setSelected(true);
					System.exit(0);
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
					
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					
				}
			});
		}
		return jCancela;
	}


	/**
	 * This method initializes jLimpar	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJLimpar() {
		if (jLimpar == null) {
			jLimpar = new JToggleButton();
			jLimpar.setBounds(new Rectangle(510, 567, 106, 22));
			jLimpar.setText("Limpar");
			jLimpar.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					cleaningFunction();
					jLimpar.setSelected(true);
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
					
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
					
				}
			});
		}
		return jLimpar;
	}
	private JToggleButton getJValoresDefault() {
		if (jValoresDefault == null) {
			jValoresDefault = new JToggleButton();
			jValoresDefault.setBounds(new Rectangle(700, 581, 127, 22));
			jValoresDefault.setText("Valores Padrão");
			jValoresDefault.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					defaultValues();
					jValoresDefault.setSelected(true);
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		return jValoresDefault;
	}



	/**
	 * This method initializes jTFCriterioParada	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFCriterioParada() {
		if (jTFCriterioParada == null) {
			jTFCriterioParada = new JTextField();
			jTFCriterioParada.setBounds(new Rectangle(77, 182, 169, 22));
		}
		return jTFCriterioParada;
		
	}


	/**
	 * This method initializes jCBCriterioParadaOptions	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBCriterioParadaOptions() {
		String[] CriterioParadaOptions = {"Por Pacotes", "Por Sessões"};

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.;
		if (jCBCriterioParadaOptions == null) {
			jCBCriterioParadaOptions = new JComboBox(CriterioParadaOptions);
			jCBCriterioParadaOptions.setBounds(new Rectangle(77, 126, 169, 22));
			jCBCriterioParadaOptions.setSelectedIndex(0);
		}
		return jCBCriterioParadaOptions;
	}


	/**
	 * This method initializes jTFCorrelIP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFCorrelIP() {
		if (jTFCorrelIP == null) {
			jTFCorrelIP = new JTextField();
			jTFCorrelIP.setBounds(new Rectangle(77, 241, 170, 20));
		}
		return jTFCorrelIP;
	}


	/**
	 * This method initializes jTFCorrelPB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFCorrelPB() {
		if (jTFCorrelPB == null) {
			jTFCorrelPB = new JTextField();
			jTFCorrelPB.setBounds(new Rectangle(77, 287, 170, 20));
		}
		return jTFCorrelPB;
	}

	/**
	 * This method initializes jTFDesvPadP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFDesvPadP() {
		if (jTFDesvPadP == null) {
			jTFDesvPadP = new JTextField();
			jTFDesvPadP.setBounds(new Rectangle(77, 385, 172, 20));
		}
		return jTFDesvPadP;
	}


	/**
	 * This method initializes jTFDesvPadI	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFDesvPadI() {
		if (jTFDesvPadI == null) {
			jTFDesvPadI = new JTextField();
			jTFDesvPadI.setBounds(new Rectangle(77, 336, 170, 20));
		}
		return jTFDesvPadI;
	}


	/**
	 * This method initializes jTFDesvPadB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFDesvPadB() {
		if (jTFDesvPadB == null) {
			jTFDesvPadB = new JTextField();
			jTFDesvPadB.setBounds(new Rectangle(77, 435, 170, 20));
		}
		return jTFDesvPadB;
	}


	/**
	 * This method initializes jTFTamPacote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFTamPacote() {
		if (jTFTamPacote == null) {
			jTFTamPacote = new JTextField();
			jTFTamPacote.setBounds(new Rectangle(637, 301, 176, 23));
		}
		return jTFTamPacote;
	}


	/**
	 * This method initializes jTFMediaChegadaSessoes	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFMediaChegadaSessoes() {
		if (jTFMediaChegadaSessoes == null) {
			jTFMediaChegadaSessoes = new JTextField();
			jTFMediaChegadaSessoes.setBounds(new Rectangle(364, 182, 176, 22));
		}
		return jTFMediaChegadaSessoes;
	}


	/**
	 * This method initializes jTFMediaTamanhoSessoes	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFMediaTamanhoSessoes() {
		if (jTFMediaTamanhoSessoes == null) {
			jTFMediaTamanhoSessoes = new JTextField();
			jTFMediaTamanhoSessoes.setBounds(new Rectangle(364, 378, 176, 22));
		}
		return jTFMediaTamanhoSessoes;
	}


	/**
	 * This method initializes jRBPacotesFixo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBPacotesFixo() {
		if (jRBPacotesFixo == null) {
			jRBPacotesFixo = new JRadioButton();
			jRBPacotesFixo.setBounds(new Rectangle(630, 154, 57, 15));
			jRBPacotesFixo.setBackground(new Color(173, 211, 254));
			jRBPacotesFixo.setText("Fixo");
		}
		return jRBPacotesFixo;
	}


	/**
	 * This method initializes jRBPacotesDistribuicao	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBPacotesDistribuicao() {
		if (jRBPacotesDistribuicao == null) {
			jRBPacotesDistribuicao = new JRadioButton();
			jRBPacotesDistribuicao.setBounds(new Rectangle(700, 154, 120, 15));
			jRBPacotesDistribuicao.setBackground(new Color(173, 211, 254));
			jRBPacotesDistribuicao.setText("Por Distribuição");
		}
		return jRBPacotesDistribuicao;
	}


	/**
	 * This method initializes jTFPacotesFixo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFPacotesFixo() {
		if (jTFPacotesFixo == null) {
			jTFPacotesFixo = new JTextField();
			jTFPacotesFixo.setBounds(new Rectangle(637, 182, 176, 22));
		}
		return jTFPacotesFixo;
	}


	/**
	 * This method initializes jCBPacotesDistribuicao	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBPacotesDistribuicao() {
		
		if (jCBPacotesDistribuicao == null) {
			jCBPacotesDistribuicao = new JComboBox(Distribuicao);
			jCBPacotesDistribuicao.setBounds(new Rectangle(637, 182, 176, 22));
			jCBPacotesDistribuicao.setSelectedIndex(0);
			
		}
		return jCBPacotesDistribuicao;
	}


	/**
	 * This method initializes jTFTipoGOP1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFTipoGOP1() {
		if (jTFTipoGOP1 == null) {
			jTFTipoGOP1 = new JTextField();
			jTFTipoGOP1.setBounds(new Rectangle(637, 357, 71, 20));
		}
		return jTFTipoGOP1;
	}


	/**
	 * This method initializes jTFTipoGOP2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFTipoGOP2() {
		if (jTFTipoGOP2 == null) {
			jTFTipoGOP2 = new JTextField();
			jTFTipoGOP2.setBounds(new Rectangle(742, 357, 72, 20));
		}
		return jTFTipoGOP2;
	}


	/**
	 * This method initializes jRBSessoesFixo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBSessoesFixo() {
		if (jRBSessoesFixo == null) {
			jRBSessoesFixo = new JRadioButton();
			jRBSessoesFixo.setBounds(new Rectangle(357, 154, 48, 17));
			jRBSessoesFixo.setBackground(new Color(173, 211, 254));
			jRBSessoesFixo.setText("Fixo");
		}
		return jRBSessoesFixo;
	}


	/**
	 * This method initializes jRBSessoesDistribuicao	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBSessoesDistribuicao() {
		if (jRBSessoesDistribuicao == null) {
			jRBSessoesDistribuicao = new JRadioButton();
			jRBSessoesDistribuicao.setBounds(new Rectangle(427, 154, 116, 17));
			jRBSessoesDistribuicao.setBackground(new Color(173, 211, 254));
			jRBSessoesDistribuicao.setText("Por Distribuição");
		}
		return jRBSessoesDistribuicao;
	}


	/**
	 * This method initializes jTFParamDist1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDist1() {
		if (jTFParamDist1 == null) {
			jTFParamDist1 = new JTextField();
			jTFParamDist1.setBounds(new Rectangle(364, 238, 71, 20));
		}
		return jTFParamDist1;
	}


	/**
	 * This method initializes jCBSessoesDistribuicao	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBSessoesDistribuicao() {
		if (jCBSessoesDistribuicao == null) {
			jCBSessoesDistribuicao = new JComboBox(Distribuicao);
			jCBSessoesDistribuicao.setBounds(new Rectangle(364, 182, 176, 22));
			jCBSessoesDistribuicao.setSelectedIndex(0);
		}
		return jCBSessoesDistribuicao;
	}


	/**
	 * This method initializes jTFParamDist2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDist2() {
		if (jTFParamDist2 == null) {
			jTFParamDist2 = new JTextField();
			jTFParamDist2.setBounds(new Rectangle(469, 238, 71, 20));
		}
		return jTFParamDist2;
	}


	/**
	 * This method initializes jRBSessoesFixo2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBSessoesFixo2() {
		if (jRBSessoesFixo2 == null) {
			jRBSessoesFixo2 = new JRadioButton();
			jRBSessoesFixo2.setBounds(new Rectangle(357, 350, 48, 17));
			jRBSessoesFixo2.setBackground(new Color(173, 211, 254));
			jRBSessoesFixo2.setText("Fixo");
		}
		return jRBSessoesFixo2;
	}


	/**
	 * This method initializes jRBSessoesDistribuicao2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRBSessoesDistribuicao2() {
		if (jRBSessoesDistribuicao2 == null) {
			jRBSessoesDistribuicao2 = new JRadioButton();
			jRBSessoesDistribuicao2.setBounds(new Rectangle(427, 350, 116, 17));
			jRBSessoesDistribuicao2.setBackground(new Color(173, 211, 254));
			jRBSessoesDistribuicao2.setText("Por Distribuição");
		}
		return jRBSessoesDistribuicao2;
	}


	/**
	 * This method initializes jTFParamDistTam	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDistTam() {
		if (jTFParamDistTam == null) {
			jTFParamDistTam = new JTextField();
			jTFParamDistTam.setBounds(new Rectangle(364, 434, 71, 20));
		}
		return jTFParamDistTam;
	}


	/**
	 * This method initializes jTFParamDistTam2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDistTam2() {
		if (jTFParamDistTam2 == null) {
			jTFParamDistTam2 = new JTextField();
			jTFParamDistTam2.setBounds(new Rectangle(469, 434, 71, 20));
		}
		return jTFParamDistTam2;
	}


	/**
	 * This method initializes jCBSessoesDistribuicaoTam	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBSessoesDistribuicaoTam() {
		if (jCBSessoesDistribuicaoTam == null) {
			jCBSessoesDistribuicaoTam = new JComboBox(Distribuicao);
			jCBSessoesDistribuicaoTam.setBounds(new Rectangle(364, 378, 176, 22));
			jCBSessoesDistribuicaoTam.setSelectedIndex(0);
		}
		return jCBSessoesDistribuicaoTam;
	}
	
	/**
	 * This method initializes jTFParamDist1Pac1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDist1Pac1() {
		if (jTFParamDist1Pac1 == null) {
			jTFParamDist1Pac1 = new JTextField();
			jTFParamDist1Pac1.setBounds(new Rectangle(637, 238, 76, 20));
		}
		return jTFParamDist1Pac1;
	}


	/**
	 * This method initializes jTFParamDist1Pac2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFParamDist1Pac2() {
		if (jTFParamDist1Pac2 == null) {
			jTFParamDist1Pac2 = new JTextField();
			jTFParamDist1Pac2.setBounds(new Rectangle(742, 238, 74, 20));
		}
		return jTFParamDist1Pac2;
	}


	/**
	 * This method initializes jTFNomeArquivo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFNomeArquivo() {
		if (jTFNomeArquivo == null) {
			jTFNomeArquivo = new JTextField();
			jTFNomeArquivo.setBounds(new Rectangle(637, 438, 197, 20));
		}
		return jTFNomeArquivo;
	}	
	

	/**
	 * This method initializes jTFErroP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFErroP() {
		if (jTFErroP == null) {
			jTFErroP = new JTextField();
			jTFErroP.setBounds(new Rectangle(77, 511, 71, 22));
		}
		return jTFErroP;
	}


	/**
	 * This method initializes jTFErroB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFErroB() {
		if (jTFErroB == null) {
			jTFErroB = new JTextField();
			jTFErroB.setBounds(new Rectangle(175, 511, 72, 22));
		}
		return jTFErroB;
	}
	
	
	/**
	 *  Auxiliar Functions
	 */
	private void cleaningFunction() {
		jTFCriterioParada.setText(null);
		jTFCorrelIP.setText(null);
		jTFCorrelPB.setText(null);
		jTFDesvPadI.setText(null);
		jTFDesvPadP.setText(null);
		jTFDesvPadB.setText(null);
		jTFMediaChegadaSessoes.setText(null);
		jTFParamDist1.setText(null);
		jTFParamDist2.setText(null);
		jTFMediaTamanhoSessoes.setText(null);
		jTFParamDistTam.setText(null);
		jTFParamDistTam2.setText(null);
		jTFParamDist1Pac1.setText(null);
		jTFParamDist1Pac2.setText(null);
		jTFPacotesFixo.setText(null);
		jTFTamPacote.setText(null);
		jTFTipoGOP1.setText(null);
		jTFTipoGOP2.setText(null);
		jTFNomeArquivo.setText(null);
		jTFErroP.setText(null);
		jTFErroB.setText(null);
		
		RetChegSes[1] = 0;
		RetChegSes[2] = 0;
		RetTamSes[1] = 0;
		RetTamSes[2] = 0;
		RetChegPac[1] = 0;
		RetChegPac[2] = 0;
	}

	/**
	 * This method initializes jValoresDefault	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private void defaultValues() {
		jTFCriterioParada.setText("5");
		
		jTFCorrelIP.setText("0.5");
		jTFCorrelPB.setText("0.5");
		
		jTFDesvPadI.setText("5");
		jTFDesvPadP.setText("5");
		jTFDesvPadB.setText("5");
		
		jTFMediaChegadaSessoes.setText("5");
		jTFParamDist1.setText("0.5");
		jTFParamDist2.setText("0");
		if(RetChegSes[0] == 0){
			RetChegSes[1] = Double.parseDouble(jTFParamDist1.getText());
			RetChegSes[2] = 0;
		}
		if(RetChegSes[0] == 1){
			RetChegSes[1] = Double.parseDouble(jTFMediaChegadaSessoes.getText());
			RetChegSes[2] = 0;
		}
		if(RetChegSes[0] == 2){
			RetChegSes[1] = Double.parseDouble(jTFParamDist1.getText());
			RetChegSes[2] = Double.parseDouble(jTFParamDist1.getText());
		}		
		
		jTFMediaTamanhoSessoes.setText("5");
		jTFParamDistTam.setText("0.5");
		jTFParamDistTam2.setText("0");
		if(RetTamSes[0] == 0){
			RetTamSes[1] = Double.parseDouble(jTFParamDistTam.getText());
			RetTamSes[2] = 0;
		}
		if(RetTamSes[0] == 1){
			RetTamSes[1] = Double.parseDouble(jTFMediaTamanhoSessoes.getText());
			RetTamSes[2] = 0;
		}
		if(RetTamSes[0] == 2){
			RetTamSes[1] = Double.parseDouble(jTFParamDistTam.getText());
			RetTamSes[2] = Double.parseDouble(jTFParamDistTam2.getText());
		}

		jTFPacotesFixo.setText("5");
		jTFParamDist1Pac1.setText("0.5");
		jTFParamDist1Pac2.setText("0");
		if(RetChegPac[0] == 0){
			RetChegPac[1] = Double.parseDouble(jTFParamDist1Pac1.getText());
			RetChegPac[2] = 0;
		}
		if(RetChegPac[0] == 1){
			RetChegPac[1] = Double.parseDouble(jTFPacotesFixo.getText());
			RetChegPac[2] = 0;
		}
		if(RetChegPac[0] == 2){
			RetChegPac[1] = Double.parseDouble(jTFParamDist1Pac1.getText());
			RetChegPac[2] = Double.parseDouble(jTFParamDist1Pac2.getText());
		}

		jTFTamPacote.setText("1500");
		
		jTFTipoGOP1.setText("12");
		jTFTipoGOP2.setText("3");
		
		jTFNomeArquivo.setText("teste");
		
		jTFErroP.setText("1");
		jTFErroB.setText("1");
		

		RetTamSes[1] = 0;
		RetTamSes[2] = 0;
		RetChegPac[1] = 0;
		RetChegPac[2] = 0;
	}
	
	private void check(){

		/**
		 *	Validation of current values in the text fields 
		 */
//----------------------------------------------------------------------------
		try{
			CriterioParada = Integer.parseInt(jTFCriterioParada.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Criterio de Parada");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------	
		try{
			CorrelIP = Double.parseDouble(jTFCorrelIP.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Correlação entre I e P");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------	
		try{
			CorrelPB = Double.parseDouble(jTFCorrelPB.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Correlação entre P e B");
			pos++;
			openPopUp = true;
		}
//----------------------------------------------------------------------------
		try{
			DesvPadI = Double.parseDouble(jTFDesvPadI.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Desvio Padrão de I");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			DesvPadP = Double.parseDouble(jTFDesvPadP.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Desvio Padrão de P");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			DesvPadB = Double.parseDouble(jTFDesvPadB.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Desvio Padrão de B");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			TamPacote = Integer.parseInt(jTFTamPacote.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Tamanho do pacote");
			pos++;
			openPopUp = true;
	    }
		

//----------------------------------------------------------------------------

		try{
			TipoGOP1 = Integer.parseInt(jTFTipoGOP1.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Primeiro parâmetro do tipo de GOP");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			TipoGOP2 = Integer.parseInt(jTFTipoGOP2.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Segundo parâmetro do tipo de GOP");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			ErroP = Double.parseDouble(jTFErroP.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Erro de P");
			pos++;
			openPopUp = true;
	    }
//----------------------------------------------------------------------------
		try{
			ErroB = Double.parseDouble(jTFErroB.getText());
		}
		catch (NumberFormatException e) {
			popup.jLErros[pos].setText("Erro de B");
			pos++;
			openPopUp = true;
	    }
		
//-------------------CHEGADA DAS SESSÕES---------------------------------------------------------
		
		if(jRBSessoesDistribuicao.isSelected()){
			try{
				ParamDist1 = Double.parseDouble(jTFParamDist1.getText());
				RetChegSes[1] = ParamDist1;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Primeiro parâmetro de chegada de Sessões");
				pos++;
				openPopUp = true;
		    }
			if(jCBSessoesDistribuicao.getSelectedIndex() == 1){
				try{
					ParamDist2 = Double.parseDouble(jTFParamDist2.getText());
					RetChegSes[2] = ParamDist2;
				}
				catch (NumberFormatException e) {
					popup.jLErros[pos].setText("Segundo parâmetro de chegada de Sessões");
					pos++;
					openPopUp = true;
			    }
			}
		}
		else if(jRBSessoesFixo.isSelected()){
			try{
				MediaChegadaSessoes = Double.parseDouble(jTFMediaChegadaSessoes.getText());
				RetChegSes[1] = ParamDist1;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Valor fixo de chegada de sessões");
				pos++;
				openPopUp = true;
		    }
		}
		
//------------------------------TAMANHO DAS SESSÕES----------------------------------------------
		
		if(jRBSessoesDistribuicao2.isSelected()){
			try{
				ParamDistTam = Double.parseDouble(jTFParamDistTam.getText());
				RetTamSes[1] = ParamDistTam;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Primeiro parâmetro de tamanho das sessões");
				pos++;
				openPopUp = true;
		    }
			if(jCBSessoesDistribuicaoTam.getSelectedIndex() == 1){
				try{
					ParamDistTam2 = Double.parseDouble(jTFParamDistTam2.getText());
					RetTamSes[2] = ParamDistTam2;
				}
				catch (NumberFormatException e) {
					popup.jLErros[pos].setText("Segundo parâmetro de tamanho das sessões");
					pos++;
					openPopUp = true;
			    }
			}
		}
		else if(jRBSessoesFixo2.isSelected()){
			try{
				MediaTamanhoSessoes = Double.parseDouble(jTFMediaTamanhoSessoes.getText());
				RetTamSes[1] = ParamDistTam;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Valor fixo de chegada das sessões");
				pos++;
				openPopUp = true;
		    }
		}
		
//----------------------------PACOTES-------------------------------------
		
		if(jRBPacotesDistribuicao.isSelected()){
			try{
				ParamDist1Pac1 = Double.parseDouble(jTFParamDist1Pac1.getText());
				RetChegPac[1] = ParamDist1Pac1;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Primeiro parâmetro de chegada dos pacotes");
				pos++;
				openPopUp = true;
		    }
			if(jCBPacotesDistribuicao.getSelectedIndex() == 1){
				try{
					ParamDist1Pac2 = Double.parseDouble(jTFParamDist1Pac2.getText());
					RetChegPac[2] = ParamDist1Pac2;
				}
				catch (NumberFormatException e) {
					popup.jLErros[pos].setText("Segundo parâmetro de chegada dos pacotes");
					pos++;
					openPopUp = true;
			    }
			}
		}
		else{
			try{
				PacotesFixo = Double.parseDouble(jTFPacotesFixo.getText());
				RetChegPac[1] = ParamDist1Pac1;
			}
			catch (NumberFormatException e) {
				popup.jLErros[pos].setText("Valor fixo de chegada dos pacotes");
				pos++;
				openPopUp = true;
			}
		}
		
//----------------------NOME DO ARQUIVO-----------------------------------------------
		try{
			NomeArquivo = jTFNomeArquivo.getText();
			
		}
		catch (Exception e) {
			popup.jLErros[pos].setText("Valor inválido para nome do arquivo");
			pos++;
			openPopUp = true;
	    }			
	}
	
	/**
	 * gets e sets dos valores utilizados no Simulator
	 * @return
	 */

	public int getCriterioParada() {
		return CriterioParada;
	}

	public int getjCBCriterioParadaOptions() {
		return jCBCriterioParadaOptions.getSelectedIndex();
	}

	public void setjCBCriterioParadaOptions(JComboBox jCBCriterioParadaOptions) {
		this.jCBCriterioParadaOptions = jCBCriterioParadaOptions;
	}


	public void setCriterioParada(int criterioParada) {
		CriterioParada = criterioParada;
	}

	public double getCorrelIP() {
		return CorrelIP;
	}

	public void setCorrelIP(double correlIP) {
		CorrelIP = correlIP;
	}

	public double getCorrelPB() {
		return CorrelPB;
	}

	public void setCorrelPB(double correlPB) {
		CorrelPB = correlPB;
	}

	public double getDesvPadP() {
		return DesvPadP;
	}

	public void setDesvPadP(double desvPadP) {
		DesvPadP = desvPadP;
	}

	public double getDesvPadI() {
		return DesvPadI;
	}

	public void setDesvPadI(double desvPadI) {
		DesvPadI = desvPadI;
	}

	public double getDesvPadB() {
		return DesvPadB;
	}

	public void setDesvPadB(double desvPadB) {
		DesvPadB = desvPadB;
	}

	public int getTamPacote() {
		return TamPacote;
	}

	public void setTamPacote(int tamPacote) {
		TamPacote = tamPacote;
	}

	public double getMediaChegadaSessoes() {
		return MediaChegadaSessoes;
	}

	public void setMediaChegadaSessoes(double mediaChegadaSessoes) {
		MediaChegadaSessoes = mediaChegadaSessoes;
	}

	public double getMediaTamanhoSessoes() {
		return MediaTamanhoSessoes;
	}

	public void setMediaTamanhoSessoes(double mediaTamanhoSessoes) {
		MediaTamanhoSessoes = mediaTamanhoSessoes;
	}

	public double getPacotesFixo() {
		return PacotesFixo;
	}

	public void setPacotesFixo(double pacotesFixo) {
		PacotesFixo = pacotesFixo;
	}

	public int getTipoGOP1() {
		return TipoGOP1;
	}

	public void setTipoGOP1(int tipoGOP1) {
		TipoGOP1 = tipoGOP1;
	}

	public int getTipoGOP2() {
		return TipoGOP2;
	}

	public void setTipoGOP2(int tipoGOP2) {
		TipoGOP2 = tipoGOP2;
	}

	public double getParamDist1() {
		return ParamDist1;
	}

	public void setParamDist1(double paramDist1) {
		ParamDist1 = paramDist1;
	}

	public double getParamDist2() {
		return ParamDist2;
	}

	public void setParamDist2(double paramDist2) {
		ParamDist2 = paramDist2;
	}

	public double getParamDistTam() {
		return ParamDistTam;
	}

	public void setParamDistTam(double paramDistTam) {
		ParamDistTam = paramDistTam;
	}

	public double getParamDistTam2() {
		return ParamDistTam2;
	}

	public void setParamDistTam2(double paramDistTam2) {
		ParamDistTam2 = paramDistTam2;
	}

	public double getParamDist1Pac1() {
		return ParamDist1Pac1;
	}

	public void setParamDist1Pac1(double paramDist1Pac1) {
		ParamDist1Pac1 = paramDist1Pac1;
	}

	public double getParamDist1Pac2() {
		return ParamDist1Pac2;
	}

	public void setParamDist1Pac2(double paramDist1Pac2) {
		ParamDist1Pac2 = paramDist1Pac2;
	}

	public double getErroP() {
		return ErroP;
	}

	public void setErroP(double erroP) {
		ErroP = erroP;
	}

	public double getErroB() {
		return ErroB;
	}

	public void setErroB(double erroB) {
		ErroB = erroB;
	}
	
	public String getNomeArquivo() {
		return NomeArquivo;
	}

	public String[] getDistribuicao() {
		return Distribuicao;
	}

	public void setDistribuicao(String[] distribuicao) {
		Distribuicao = distribuicao;
	}
	
	public double[] getRetChegSes(){
		return RetChegSes;
	}
	public double[] getRetTamSes(){
		return RetTamSes;
	}
	public double[] getRetChegPac(){
		return RetChegPac;
	}

	
}
