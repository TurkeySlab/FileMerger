import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.net.URISyntaxException;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldDestinationFolder;
	private JTextField txtFieldFromFolder1;
	private JTextField txtFieldFromFolder2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		//magic
					
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setResizable(false);
		
		String dir = "";
		try {
			dir = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
			System.out.println(dir);
		} catch (URISyntaxException e) {
			// uhhh 
			e.printStackTrace();
		}
		
		Merge toMerge = new Merge(dir);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
	// Labels
		JTextPane txtpnThisAppletMerges = new JTextPane();
		txtpnThisAppletMerges.setBackground(Color.DARK_GRAY);
		txtpnThisAppletMerges.setForeground(Color.GREEN);
		txtpnThisAppletMerges.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtpnThisAppletMerges.setText("This applet merges 2 folders whos contents are known to be unique but when joined the OS detects that there are duplicate names.\r\nSolves this problem by creaqting a new Primary Key name for each file. \r\nWhere the file in 'Destination' \r\n     = <date-last-modified:MM-dd-yyyy> + <uniqueNum> + .<type>\"");
		txtpnThisAppletMerges.setBounds(10, 134, 554, 86);
		panel.add(txtpnThisAppletMerges);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setForeground(Color.GREEN);
		lblDestination.setFont(new Font("Monospaced", Font.PLAIN, 11));
		lblDestination.setToolTipText("Where the files will be placed. For simplicity, place the app in the destination");
		lblDestination.setBounds(10, 11, 84, 14);
		panel.add(lblDestination);
		
		JLabel lblFolder = new JLabel("Folder 1");
		lblFolder.setToolTipText("Folder to pull files from first");
		lblFolder.setForeground(Color.GREEN);
		lblFolder.setFont(new Font("Monospaced", Font.PLAIN, 11));
		lblFolder.setBounds(10, 60, 84, 14);
		panel.add(lblFolder);
		
		JLabel lblFolder_2 = new JLabel("Folder 2");
		lblFolder_2.setToolTipText("Folder to pull files from AFTER 'Folder 1' is empty");
		lblFolder_2.setForeground(Color.GREEN);
		lblFolder_2.setFont(new Font("Monospaced", Font.PLAIN, 11));
		lblFolder_2.setBounds(10, 99, 84, 14);
		panel.add(lblFolder_2);
		
	// Inputs	
		txtFieldDestinationFolder = new JTextField();
		txtFieldDestinationFolder.setToolTipText("Where renamed files will be placed");
		txtFieldDestinationFolder.setText(dir);
		txtFieldDestinationFolder.setBounds(105, 8, 459, 20);
		panel.add(txtFieldDestinationFolder);
		txtFieldDestinationFolder.setColumns(10);
		
		txtFieldFromFolder1 = new JTextField();
		txtFieldFromFolder1.setToolTipText("directory location of folder 1");
		txtFieldFromFolder1.setText(dir + "\\A");
		txtFieldFromFolder1.setColumns(10);
		txtFieldFromFolder1.setBounds(105, 57, 459, 20);
		panel.add(txtFieldFromFolder1);
		
		txtFieldFromFolder2 = new JTextField();
		txtFieldFromFolder2.setToolTipText("directory location of folder 2");
		txtFieldFromFolder2.setText(dir + "\\B");
		txtFieldFromFolder2.setColumns(10);
		txtFieldFromFolder2.setBounds(105, 96, 459, 20);
		panel.add(txtFieldFromFolder2);
		
		JButton btnDoIt = new JButton("Do it");
		btnDoIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toMerge.setFolderA(txtFieldFromFolder1.getText());
				toMerge.setFolderB(txtFieldFromFolder2.getText());
				
				if(toMerge.merge())
					JOptionPane.showMessageDialog(panel, "Success");
				else
					JOptionPane.showMessageDialog(panel, "One of your folders are empty");
				
			}
		});
		btnDoIt.setBounds(230, 225, 100, 25);
		panel.add(btnDoIt);
	}
}
