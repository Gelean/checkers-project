// NewGameDialog.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewGameDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JRadioButton blackHumanButton, blackAIButton, whiteHumanButton, whiteAIButton;
	private JTextField blackPlies, whitePlies, fileName, secondsPerPlayer;
	private JCheckBox useFileCheckBox;
	private JButton okButton, cancelButton;
	private boolean okPressed;

	public NewGameDialog()
	{
		super((JFrame)null, "Start New Game", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		okPressed = false;
		buildUI();
		setSize(500, 400);
	}

	public boolean okPressed()
	{
		return okPressed;
	}

	public boolean blackIsHuman()
	{
		return blackHumanButton.isSelected();
	}
	
	public boolean whiteIsHuman()
	{
		return whiteHumanButton.isSelected();
	}

	public int blackMaxPlies()
	{
	    String p = blackPlies.getText();
	    int plies = 1;
	    try
	    {
	    	plies = Integer.parseInt(p);
	    }
	    catch(NumberFormatException e) {}
	    if(plies < 1)
	    	plies = 1;
	    return plies;
	}

	public int whiteMaxPlies()
	{
	    String p = whitePlies.getText();
	    int plies = 1;
	    try
	    {
	    	plies = Integer.parseInt(p);
	    }
	    catch(NumberFormatException e) {}
	    if(plies < 1)
	    	plies = 1;
	    return plies;
	}

	public int secondsPerPlayer()
	{
	    String s = secondsPerPlayer.getText();
	    int seconds = 300;
	    try
	    {
	    	seconds = Integer.parseInt(s);
	    }
	    catch(NumberFormatException e) {}
	    return seconds;
	}

	public String getFileName() //
	{
	    if(useFileCheckBox.isSelected())
	        return fileName.getText();
	    else
	        return null;
	}

	private void buildUI()
	{
		Container p = getContentPane();
		GridBagLayout g = new GridBagLayout();
		p.setLayout(g);
		//Change the window size
		GridBagConstraints c = new GridBagConstraints(0, 0, 0, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0);

		// row 0
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		JLabel blackLabel = new JLabel("Black");
		p.add(blackLabel);
		g.setConstraints(blackLabel, c);

		// row 1
		ButtonGroup group1 = new ButtonGroup();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		blackHumanButton = new JRadioButton("Human", true);
		group1.add(blackHumanButton);
		p.add(blackHumanButton);
		g.setConstraints(blackHumanButton, c);

		c.gridx = 1;
		blackAIButton = new JRadioButton("AI        ", false);
		group1.add(blackAIButton);
		p.add(blackAIButton);
		g.setConstraints(blackAIButton, c);

		c.gridx = 2;
		JLabel blackPlyLabel = new JLabel("            		Plies:");
		p.add(blackPlyLabel);
		g.setConstraints(blackPlyLabel, c);

		c.gridx = 3;
		blackPlies = new JTextField("2", 2);
		p.add(blackPlies);
		g.setConstraints(blackPlies, c);

		// row 2
		/*
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		blackUseAB = new JCheckBox("Use alpha-beta pruning");
		p.add(blackUseAB);
		g.setConstraints(blackUseAB, c);

		// row 3
		ButtonGroup group2 = new ButtonGroup();
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		blackSimpleButton = new JRadioButton("Simple Board Eval", true);
		group2.add(blackSimpleButton);
		p.add(blackSimpleButton);
		g.setConstraints(blackSimpleButton, c);

		// row 4
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		blackCustomButton = new JRadioButton("Custom Board Eval");
		group2.add(blackCustomButton);
		p.add(blackCustomButton);
		g.setConstraints(blackCustomButton, c);
		*/
		
		// row 5
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.CENTER;
		JLabel whiteLabel = new JLabel("White");
		p.add(whiteLabel);
		g.setConstraints(whiteLabel, c);

		// row 6
		ButtonGroup group3 = new ButtonGroup();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		whiteHumanButton = new JRadioButton("Human", true);
		group3.add(whiteHumanButton);
		p.add(whiteHumanButton);
		g.setConstraints(whiteHumanButton, c);

		c.gridx = 1;
		whiteAIButton = new JRadioButton("AI        ", false);
		group3.add(whiteAIButton);
		p.add(whiteAIButton);
		g.setConstraints(whiteAIButton, c);

		c.gridx = 2;
		JLabel whitePlyLabel = new JLabel("           		Plies:");
		p.add(whitePlyLabel);
		g.setConstraints(whitePlyLabel, c);

		c.gridx = 3;
		whitePlies = new JTextField("2", 2);
		p.add(whitePlies);
		g.setConstraints(whitePlies, c);

		// row 7
		/*
		c.gridx = 2;
		c.gridy = 7;
		c.gridwidth = 1;
		whiteUseAB = new JCheckBox("Use alpha-beta pruning");
		p.add(whiteUseAB);
		g.setConstraints(whiteUseAB, c);

		// row 8
		ButtonGroup group4 = new ButtonGroup();
		c.gridx = 2;
		c.gridy = 8;
		c.gridwidth = 1;
		whiteSimpleButton = new JRadioButton("Simple Board Eval", true);
		group4.add(whiteSimpleButton);
		p.add(whiteSimpleButton);
		g.setConstraints(whiteSimpleButton, c);

		// row 9
		c.gridx = 2;
		c.gridy = 9;
		c.gridwidth = 1;
		whiteCustomButton = new JRadioButton("Custom Board Eval");
		group4.add(whiteCustomButton);
		p.add(whiteCustomButton);
		g.setConstraints(whiteCustomButton, c);
		*/
		
		// row 10
		c.gridx = 0;
		c.gridy = 10;
		JLabel lab1 = new JLabel("Seconds per player:");
		p.add(lab1);
		g.setConstraints(lab1, c);

		c.gridx = 2;
		secondsPerPlayer = new JTextField("300", 4);
		p.add(secondsPerPlayer);
		g.setConstraints(secondsPerPlayer, c);

		// row 11
		c.gridx = 0;
		c.gridy = 11;
		useFileCheckBox = new JCheckBox("Start from file position:");
		p.add(useFileCheckBox);
		g.setConstraints(useFileCheckBox, c);

		c.gridx = 2;
		fileName = new JTextField("", 8);
		p.add(fileName);
		g.setConstraints(fileName, c);

		// row 12
		c.gridx = 2;
		c.gridy = 12;
		c.anchor = GridBagConstraints.EAST;
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		p.add(okButton);
		g.setConstraints(okButton, c);

		c.gridx = 3;
		c.anchor = GridBagConstraints.WEST;
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		p.add(cancelButton);
		g.setConstraints(cancelButton, c);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource();
		if(obj == okButton)
		{
			okPressed = true;
			processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if(obj == cancelButton)
		{
			okPressed = false;
			this.dispose();
		}
	}
}
