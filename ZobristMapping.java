import java.io.Serializable;

/*****************************************************************************************************************/
//******************************NEVER NEVER NEVER NEVER NEVER NEVER CHANGE VALUES********************************//
/*****************************************************************************************************************/

public class ZobristMapping implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String A1a,A1b,A1c,A1d,A1e,A2a,A2b,A2c,A2d,A2e,A3a,A3b,A3c,A3d,A3e,A4a,A4b,A4c,A4d,A4e;
	private String B1a,B1b,B1c,B1d,B1e,B2a,B2b,B2c,B2d,B2e,B3a,B3b,B3c,B3d,B3e,B4a,B4b,B4c,B4d,B4e;
	private String C1a,C1b,C1c,C1d,C1e,C2a,C2b,C2c,C2d,C2e,C3a,C3b,C3c,C3d,C3e,C4a,C4b,C4c,C4d,C4e;
	private String D1a,D1b,D1c,D1d,D1e,D2a,D2b,D2c,D2d,D2e,D3a,D3b,D3c,D3d,D3e,D4a,D4b,D4c,D4d,D4e;
	private String E1a,E1b,E1c,E1d,E1e,E2a,E2b,E2c,E2d,E2e,E3a,E3b,E3c,E3d,E3e,E4a,E4b,E4c,E4d,E4e;
	private String F1a,F1b,F1c,F1d,F1e,F2a,F2b,F2c,F2d,F2e,F3a,F3b,F3c,F3d,F3e,F4a,F4b,F4c,F4d,F4e;
	private String G1a,G1b,G1c,G1d,G1e,G2a,G2b,G2c,G2d,G2e,G3a,G3b,G3c,G3d,G3e,G4a,G4b,G4c,G4d,G4e;
	private String H1a,H1b,H1c,H1d,H1e,H2a,H2b,H2c,H2d,H2e,H3a,H3b,H3c,H3d,H3e,H4a,H4b,H4c,H4d,H4e;
	
	public ZobristMapping()
	{ 
		//a = EMPTY, b=BLACK, c=WHITE,d=BLACKKING,e=WHITEKING
		//A = row 0, B = row 1, .... , G = row 7
		//1 = legal square 1, 2=legal square 2, ...
		//hard coded random values for each board position
		
		int temp = 638199801;
		A1a = Integer.toBinaryString(temp);
		temp = 409254253;
		A1b = Integer.toBinaryString(temp);
		temp = 584106832;
		A1c = Integer.toBinaryString(temp);
		temp = 224615601;
		A1d = Integer.toBinaryString(temp);
		temp = 574839210;
		A1e = Integer.toBinaryString(temp);
		
		temp = 780413745;
		A2a = Integer.toBinaryString(temp);
		temp = 612476138;
		A2b = Integer.toBinaryString(temp);
		temp = 460824669;
		A2c = Integer.toBinaryString(temp);
		temp = 926290466;
		A2d = Integer.toBinaryString(temp);
		temp = 985887580;
		A2e = Integer.toBinaryString(temp);
		
		temp =385922285;
		A3a = Integer.toBinaryString(temp);
		temp = 356898335;
		A3b = Integer.toBinaryString(temp);
		temp = 1699193;
		A3c = Integer.toBinaryString(temp);
		temp = 194320830;
		A3d = Integer.toBinaryString(temp);
		temp = 930458112;
		A3e = Integer.toBinaryString(temp);
		
		temp = 240169347;
		A4a = Integer.toBinaryString(temp);
		temp = 720523427;
		A4b = Integer.toBinaryString(temp);
		temp = 1771724;
		A4c = Integer.toBinaryString(temp);
		temp = 939512497;
		A4d = Integer.toBinaryString(temp);
		temp = 414988082;
		A4e = Integer.toBinaryString(temp);
		
		temp = 591729117;
		B1a = Integer.toBinaryString(temp);
		temp = 917368322;
		B1b = Integer.toBinaryString(temp);
		temp = 574200421;
		B1c = Integer.toBinaryString(temp);
		temp = 256983871;
		B1d = Integer.toBinaryString(temp);
		temp = 332256055;
		B1e = Integer.toBinaryString(temp);
		
		temp = 805663519;
		B2a = Integer.toBinaryString(temp);
		temp = 138836697;
		B2b = Integer.toBinaryString(temp);
		temp = 311867101;
		B2c = Integer.toBinaryString(temp);
		temp = 896704849;
		B2d = Integer.toBinaryString(temp);
		temp = 414434033;
		B2e = Integer.toBinaryString(temp);
		
		temp = 934352338;
		B3a = Integer.toBinaryString(temp);
		temp = 86713803;
		B3b = Integer.toBinaryString(temp);
		temp = 66678566;
		B3c = Integer.toBinaryString(temp);
		temp = 903580730;
		B3d = Integer.toBinaryString(temp);
		temp = 125201263;
		B3e = Integer.toBinaryString(temp);
		
		temp = 151043385;
		B4a = Integer.toBinaryString(temp);
		temp = 773694744;
		B4b = Integer.toBinaryString(temp);
		temp = 143423834;
		B4c = Integer.toBinaryString(temp);
		temp = 792820218;
		B4d = Integer.toBinaryString(temp);
		temp = 703674403;
		B4e = Integer.toBinaryString(temp);
		
		temp = 401311306;
		C1a = Integer.toBinaryString(temp);
		temp = 132319575;
		C1b = Integer.toBinaryString(temp);
		temp = 786592312;
		C1c = Integer.toBinaryString(temp);
		temp = 55628257;
		C1d = Integer.toBinaryString(temp);
		temp = 353348742;
		C1e = Integer.toBinaryString(temp);
		
		temp = 822097721;
		C2a = Integer.toBinaryString(temp);
		temp = 672618691;
		C2b = Integer.toBinaryString(temp);
		temp = 574920720;
		C2c = Integer.toBinaryString(temp);
		temp = 847605537;
		C2d = Integer.toBinaryString(temp);
		temp = 299751166;
		C2e = Integer.toBinaryString(temp);
		
		temp = 851689321;
		C3a = Integer.toBinaryString(temp);
		temp = 803585001;
		C3b = Integer.toBinaryString(temp);
		temp = 75576058;
		C3c = Integer.toBinaryString(temp);
		temp = 115973652;
		C3d = Integer.toBinaryString(temp);
		temp = 988748943;
		C3e = Integer.toBinaryString(temp);
		
		temp = 23572365;
		C4a = Integer.toBinaryString(temp);
		temp = 557403210;
		C4b = Integer.toBinaryString(temp);
		temp = 516853470;
		C4c = Integer.toBinaryString(temp);
		temp = 428341052;
		C4d = Integer.toBinaryString(temp);
		temp = 733878954;
		C4e = Integer.toBinaryString(temp);
		
		temp = 709919262;
		D1a = Integer.toBinaryString(temp);
		temp = 743825356;
		D1b = Integer.toBinaryString(temp);
		temp = 902261853;
		D1c = Integer.toBinaryString(temp);
		temp = 465717809;
		D1d = Integer.toBinaryString(temp);
		temp = 385539933;
		D1e = Integer.toBinaryString(temp);
		
		temp = 879044342;
		D2a = Integer.toBinaryString(temp);
		temp = 630972617;
		D2b = Integer.toBinaryString(temp);
		temp = 818225012;
		D2c = Integer.toBinaryString(temp);
		temp = 990602767;
		D2d = Integer.toBinaryString(temp);
		temp = 612601175;
		D2e = Integer.toBinaryString(temp);
		
		temp = 314556428;
		D3a = Integer.toBinaryString(temp);
		temp = 839502458;
		D3b = Integer.toBinaryString(temp);
		temp = 283323420;
		D3c = Integer.toBinaryString(temp);
		temp = 37042035;
		D3d = Integer.toBinaryString(temp);
		temp = 995096198;
		D3e = Integer.toBinaryString(temp);
		
		temp = 704524139;
		D4a = Integer.toBinaryString(temp);
		temp = 189461462;
		D4b = Integer.toBinaryString(temp);
		temp = 57184983;
		D4c = Integer.toBinaryString(temp);
		temp = 308521405;
		D4d = Integer.toBinaryString(temp);
		temp = 222812438;
		D4e = Integer.toBinaryString(temp);
		
		temp = 159496434;
		E1a = Integer.toBinaryString(temp);
		temp = 985977544;
		E1b = Integer.toBinaryString(temp);
		temp = 790929170;
		E1c = Integer.toBinaryString(temp);
		temp = 117674957;
		E1d = Integer.toBinaryString(temp);
		temp = 623811081;
		E1e = Integer.toBinaryString(temp);
		
		temp = 253538242;
		E2a = Integer.toBinaryString(temp);
		temp = 648181842;
		E2b = Integer.toBinaryString(temp);
		temp = 236697545;
		E2c = Integer.toBinaryString(temp);
		temp = 468455133;
		E2d = Integer.toBinaryString(temp);
		temp = 170026355;
		E2e = Integer.toBinaryString(temp);
		
		temp = 486755830;
		E3a = Integer.toBinaryString(temp);
		temp = 374670436;
		E3b = Integer.toBinaryString(temp);
		temp = 696420303;
		E3c = Integer.toBinaryString(temp);
		temp = 193140277;
		E3d = Integer.toBinaryString(temp);
		temp = 357797049;
		E3e = Integer.toBinaryString(temp);
		
		temp = 595435977;
		E4a = Integer.toBinaryString(temp);
		temp = 83932335;
		E4b = Integer.toBinaryString(temp);
		temp = 113004755;
		E4c = Integer.toBinaryString(temp);
		temp = 246313987;
		E4d = Integer.toBinaryString(temp);
		temp = 556962444;
		E4e = Integer.toBinaryString(temp);
		
		temp = 14324457;
		F1a = Integer.toBinaryString(temp);
		temp = 784510644;
		F1b = Integer.toBinaryString(temp);
		temp = 687460993;
		F1c = Integer.toBinaryString(temp);
		temp = 385317682;
		F1d = Integer.toBinaryString(temp);
		temp = 323512999;
		F1e = Integer.toBinaryString(temp);
		
		temp = 169966204;
		F2a = Integer.toBinaryString(temp);
		temp = 759525267;
		F2b = Integer.toBinaryString(temp);
		temp = 327197356;
		F2c = Integer.toBinaryString(temp);
		temp = 318339226;
		F2d = Integer.toBinaryString(temp);
		temp = 481549388;
		F2e = Integer.toBinaryString(temp);
		
		temp = 148136362;
		F3a = Integer.toBinaryString(temp);
		temp = 230459690;
		F3b = Integer.toBinaryString(temp);
		temp = 140008042;
		F3c = Integer.toBinaryString(temp);
		temp = 793222926;
		F3d = Integer.toBinaryString(temp);
		temp = 180563998;
		F3e = Integer.toBinaryString(temp);
		
		temp = 810402190;
		F4a = Integer.toBinaryString(temp);
		temp = 438750601;
		F4b = Integer.toBinaryString(temp);
		temp = 270735747;
		F4c = Integer.toBinaryString(temp);
		temp = 273603156;
		F4d = Integer.toBinaryString(temp);
		temp = 321183913;
		F4e = Integer.toBinaryString(temp);
		
		temp = 863306804;
		G1a = Integer.toBinaryString(temp);
		temp = 460061195;
		G1b = Integer.toBinaryString(temp);
		temp = 469822202;
		G1c = Integer.toBinaryString(temp);
		temp = 59249205;
		G1d = Integer.toBinaryString(temp);
		temp = 108026786;
		G1e = Integer.toBinaryString(temp);
		
		temp = 921773197;
		G2a = Integer.toBinaryString(temp);
		temp = 838083684;
		G2b = Integer.toBinaryString(temp);
		temp = 553004811;
		G2c = Integer.toBinaryString(temp);
		temp = 775079144;
		G2d = Integer.toBinaryString(temp);
		temp = 682434567;
		G2e = Integer.toBinaryString(temp);
		
		temp = 492058488;
		G3a = Integer.toBinaryString(temp);
		temp = 408700149;
		G3b = Integer.toBinaryString(temp);
		temp = 363935362;
		G3c = Integer.toBinaryString(temp);
		temp = 384118324;
		G3d = Integer.toBinaryString(temp);
		temp = 45573655;
		G3e = Integer.toBinaryString(temp);
		
		temp = 787664391;
		G4a = Integer.toBinaryString(temp);
		temp = 80275938;
		G4b = Integer.toBinaryString(temp);
		temp = 903873858;
		G4c = Integer.toBinaryString(temp);
		temp = 143757296;
		G4d = Integer.toBinaryString(temp);
		temp = 377844436;
		G4e = Integer.toBinaryString(temp);
		
		temp = 803681053;
		H1a = Integer.toBinaryString(temp);
		temp = 276127243;
		H1b = Integer.toBinaryString(temp);
		temp = 354454148;
		H1c = Integer.toBinaryString(temp);
		temp = 163673341;
		H1d = Integer.toBinaryString(temp);
		temp = 996532526;
		H1e = Integer.toBinaryString(temp);
		
		temp = 402728061;
		H2a = Integer.toBinaryString(temp);
		temp = 140757763;
		H2b = Integer.toBinaryString(temp);
		temp = 425285881;
		H2c = Integer.toBinaryString(temp);
		temp = 226407018;
		H2d = Integer.toBinaryString(temp);
		temp = 383975295;
		H2e = Integer.toBinaryString(temp);
		
		temp = 720554381;
		H3a = Integer.toBinaryString(temp);
		temp = 177315061;
		H3b = Integer.toBinaryString(temp);
		temp = 999149484;
		H3c = Integer.toBinaryString(temp);
		temp = 664401319;
		H3d = Integer.toBinaryString(temp);
		temp = 850793933;
		H3e = Integer.toBinaryString(temp);
		
		temp = 793819048;
		H4a = Integer.toBinaryString(temp);
		temp = 426331523;
		H4b = Integer.toBinaryString(temp);
		temp = 47120364;
		H4c = Integer.toBinaryString(temp);
		temp = 305091440;
		H4d = Integer.toBinaryString(temp);
		temp = 68805389;
		H4e = Integer.toBinaryString(temp);
	}

	public String getMoveString(int row,int col, int color)
	{
		if(color == Board.EMPTY)
			return getEmptyString(row,col);
		else if(color == Board.BLACK)
		{
			return getBlackString(row,col);
		}
		else if(color == Board.WHITE)
		{
			return getWhiteString(row,col);
		}
		else if(color == Board.BLACKKING)
		{
			return getBlackKingString(row,col);
		}
		else if(color == Board.WHITEKING)
		{
			return getWhiteKingString(row,col);
		}
		return "";
	}
/****************************EMPTY PIECE FOR Each Square*****************************/
	private String getEmptyString(int row, int col)
	{
		if(row < 4)
		{
			if(row==0)
				return getEmptyString0(col);
			else if(row ==1)
				return getEmptyString1(col);
			else if(row ==2)
				return getEmptyString2(col);
			else if(row ==3)
				return getEmptyString3(col);
		}
		else
		{
			if(row ==4)
				return getEmptyString4(col);
			else if(row ==5)
				return getEmptyString5(col);
			else if(row ==6)
				return getEmptyString6(col);
			else if(row ==7)
				return getEmptyString7(col);
		}	
		return "";
	}
	
	
	private String getEmptyString0(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return A1a;
			else if(col ==3)
				return A2a;
		}
		else
		{
			if(col ==5)
				return A3a;
			else if(col ==7)
				return A4a;
		}
		return "";
	}
	
	private String getEmptyString1(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return B1a;
			else if(col == 2)
				return B2a;
		}
		else
		{
			if(col == 4)
				return B3a;
			else if(col == 6)
				return B4a;
		}
		return "";
	}
	
	private String getEmptyString2(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return C1a;
			else if(col ==3)
				return C2a;
		}
		else
		{
			if(col ==5)
				return C3a;
			else if(col ==7)
				return C4a;
		}
		return "";
	}
	
	private String getEmptyString3(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return D1a;
			else if(col == 2)
				return D2a;
		}
		else
		{
			if(col == 4)
				return D3a;
			else if(col == 6)
				return D4a;
		}
		return "";
	}
	
	private String getEmptyString4(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return E1a;
			else if(col ==3)
				return E2a;
		}
		else
		{
			if(col ==5)
				return E3a;
			else if(col ==7)
				return E4a;
		}
		return "";
	}
	
	private String getEmptyString5(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return F1a;
			else if(col == 2)
				return F2a;
		}
		else
		{
			if(col == 4)
				return F3a;
			else if(col == 6)
				return F4a;
		}
		return "";
	}
	
	private String getEmptyString6(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return G1a;
			else if(col ==3)
				return G2a;
		}
		else
		{
			if(col ==5)
				return G3a;
			else if(col ==7)
				return G4a;
		}
		return "";
	}
	
	private String getEmptyString7(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return H1a;
			else if(col == 2)
				return H2a;
		}
		else
		{
			if(col == 4)
				return H3a;
			else if(col == 6)
				return H4a;
		}
		return "";
	}
/**************************BLACK PIECE AT EACH SQUARE*****************************************/
	private String getBlackString(int row, int col)
	{
		if(row < 4)
		{
			if(row==0)
				return getBlackString0(col);
			else if(row ==1)
				return getBlackString1(col);
			else if(row ==2)
				return getBlackString2(col);
			else if(row ==3)
				return getBlackString3(col);
		}
		else
		{
			if(row ==4)
				return getBlackString4(col);
			else if(row ==5)
				return getBlackString5(col);
			else if(row ==6)
				return getBlackString6(col);
			else if(row ==7)
				return getBlackString7(col);
		}	
		return "";
	}
	
	
	private String getBlackString0(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return A1b;
			else if(col ==3)
				return A2b;
		}
		else
		{
			if(col ==5)
				return A3b;
			else if(col ==7)
				return A4b;
		}
		return "";
	}
	
	private String getBlackString1(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return B1b;
			else if(col == 2)
				return B2b;
		}
		else
		{
			if(col == 4)
				return B3b;
			else if(col == 6)
				return B4b;
		}
		return "";
	}
	
	private String getBlackString2(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return C1b;
			else if(col ==3)
				return C2b;
		}
		else
		{
			if(col ==5)
				return C3b;
			else if(col ==7)
				return C4b;
		}
		return "";
	}
	
	private String getBlackString3(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return D1b;
			else if(col == 2)
				return D2b;
		}
		else
		{
			if(col == 4)
				return D3b;
			else if(col == 6)
				return D4b;
		}
		return "";
	}
	
	private String getBlackString4(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return E1b;
			else if(col ==3)
				return E2b;
		}
		else
		{
			if(col ==5)
				return E3b;
			else if(col ==7)
				return E4b;
		}
		return "";
	}
	
	private String getBlackString5(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return F1b;
			else if(col == 2)
				return F2b;
		}
		else
		{
			if(col == 4)
				return F3b;
			else if(col == 6)
				return F4b;
		}
		return "";
	}
	
	private String getBlackString6(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return G1b;
			else if(col ==3)
				return G2b;
		}
		else
		{
			if(col ==5)
				return G3b;
			else if(col ==7)
				return G4b;
		}
		return "";
	}
	
	private String getBlackString7(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return H1b;
			else if(col == 2)
				return H2b;
		}
		else
		{
			if(col == 4)
				return H3b;
			else if(col == 6)
				return H4b;
		}
		return "";
	}
/**********************************WHITE PIECE FOR EACH SQUARE***********************************************/
	private String getWhiteString(int row, int col)
	{
		if(row < 4)
		{
			if(row==0)
				return getWhiteString0(col);
			else if(row ==1)
				return getWhiteString1(col);
			else if(row ==2)
				return getWhiteString2(col);
			else if(row ==3)
				return getWhiteString3(col);
		}
		else
		{
			if(row ==4)
				return getWhiteString4(col);
			else if(row ==5)
				return getWhiteString5(col);
			else if(row ==6)
				return getWhiteString6(col);
			else if(row ==7)
				return getWhiteString7(col);
		}	
		return "";
	}
	
	
	private String getWhiteString0(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return A1c;
			else if(col ==3)
				return A2c;
		}
		else
		{
			if(col ==5)
				return A3c;
			else if(col ==7)
				return A4c;
		}
		return "";
	}
	
	private String getWhiteString1(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return B1c;
			else if(col == 2)
				return B2c;
		}
		else
		{
			if(col == 4)
				return B3c;
			else if(col == 6)
				return B4c;
		}
		return "";
	}
	
	private String getWhiteString2(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return C1c;
			else if(col ==3)
				return C2c;
		}
		else
		{
			if(col ==5)
				return C3c;
			else if(col ==7)
				return C4c;
		}
		return "";
	}
	
	private String getWhiteString3(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return D1c;
			else if(col == 2)
				return D2c;
		}
		else
		{
			if(col == 4)
				return D3c;
			else if(col == 6)
				return D4c;
		}
		return "";
	}
	
	private String getWhiteString4(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return E1c;
			else if(col ==3)
				return E2c;
		}
		else
		{
			if(col ==5)
				return E3c;
			else if(col ==7)
				return E4c;
		}
		return "";
	}
	
	private String getWhiteString5(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return F1c;
			else if(col == 2)
				return F2c;
		}
		else
		{
			if(col == 4)
				return F3c;
			else if(col == 6)
				return F4c;
		}
		return "";
	}
	
	private String getWhiteString6(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return G1c;
			else if(col ==3)
				return G2c;
		}
		else
		{
			if(col ==5)
				return G3c;
			else if(col ==7)
				return G4c;
		}
		return "";
	}
	
	private String getWhiteString7(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return H1c;
			else if(col == 2)
				return H2c;
		}
		else
		{
			if(col == 4)
				return H3c;
			else if(col == 6)
				return H4c;
		}
		return "";
	}
/*****************************BLACKKING FOR EACH SQUARE*******************************************/
	private String getBlackKingString(int row, int col)
	{
		if(row < 4)
		{
			if(row==0)
				return getBlackKingString0(col);
			else if(row ==1)
				return getBlackKingString1(col);
			else if(row ==2)
				return getBlackKingString2(col);
			else if(row ==3)
				return getBlackKingString3(col);
		}
		else
		{
			if(row ==4)
				return getBlackKingString4(col);
			else if(row ==5)
				return getBlackKingString5(col);
			else if(row ==6)
				return getBlackKingString6(col);
			else if(row ==7)
				return getBlackKingString7(col);
		}	
		return "";
	}
	
	
	private String getBlackKingString0(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return A1d;
			else if(col ==3)
				return A2d;
		}
		else
		{
			if(col ==5)
				return A3d;
			else if(col ==7)
				return A4d;
		}
		return "";
	}
	
	private String getBlackKingString1(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return B1d;
			else if(col == 2)
				return B2d;
		}
		else
		{
			if(col == 4)
				return B3d;
			else if(col == 6)
				return B4d;
		}
		return "";
	}
	
	private String getBlackKingString2(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return C1d;
			else if(col ==3)
				return C2d;
		}
		else
		{
			if(col ==5)
				return C3d;
			else if(col ==7)
				return C4d;
		}
		return "";
	}
	
	private String getBlackKingString3(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return D1d;
			else if(col == 2)
				return D2d;
		}
		else
		{
			if(col == 4)
				return D3d;
			else if(col == 6)
				return D4d;
		}
		return "";
	}
	
	private String getBlackKingString4(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return E1d;
			else if(col ==3)
				return E2d;
		}
		else
		{
			if(col ==5)
				return E3d;
			else if(col ==7)
				return E4d;
		}
		return "";
	}
	
	private String getBlackKingString5(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return F1d;
			else if(col == 2)
				return F2d;
		}
		else
		{
			if(col == 4)
				return F3d;
			else if(col == 6)
				return F4d;
		}
		return "";
	}
	
	private String getBlackKingString6(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return G1d;
			else if(col ==3)
				return G2d;
		}
		else
		{
			if(col ==5)
				return G3d;
			else if(col ==7)
				return G4d;
		}
		return "";
	}
	
	private String getBlackKingString7(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return H1d;
			else if(col == 2)
				return H2d;
		}
		else
		{
			if(col == 4)
				return H3d;
			else if(col == 6)
				return H4d;
		}
		return "";
	}
	
/******************************WHITE KING FOR EACH SQUARE**************************************/
	private String getWhiteKingString(int row, int col)
	{
		if(row < 4)
		{
			if(row==0)
				return getWhiteKingString0(col);
			else if(row ==1)
				return getWhiteKingString1(col);
			else if(row ==2)
				return getWhiteKingString2(col);
			else if(row ==3)
				return getWhiteKingString3(col);
		}
		else
		{
			if(row ==4)
				return getWhiteKingString4(col);
			else if(row ==5)
				return getWhiteKingString5(col);
			else if(row ==6)
				return getWhiteKingString6(col);
			else if(row ==7)
				return getWhiteKingString7(col);
		}	
		return "";
	}
	
	
	private String getWhiteKingString0(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return A1e;
			else if(col ==3)
				return A2e;
		}
		else
		{
			if(col ==5)
				return A3e;
			else if(col ==7)
				return A4e;
		}
		return "";
	}
	
	private String getWhiteKingString1(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return B1e;
			else if(col == 2)
				return B2e;
		}
		else
		{
			if(col == 4)
				return B3e;
			else if(col == 6)
				return B4e;
		}
		return "";
	}
	
	private String getWhiteKingString2(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return C1e;
			else if(col ==3)
				return C2e;
		}
		else
		{
			if(col ==5)
				return C3e;
			else if(col ==7)
				return C4e;
		}
		return "";
	}
	
	private String getWhiteKingString3(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return D1e;
			else if(col == 2)
				return D2e;
		}
		else
		{
			if(col == 4)
				return D3e;
			else if(col == 6)
				return D4e;
		}
		return "";
	}
	
	private String getWhiteKingString4(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return E1e;
			else if(col ==3)
				return E2e;
		}
		else
		{
			if(col ==5)
				return E3e;
			else if(col ==7)
				return E4e;
		}
		return "";
	}
	
	private String getWhiteKingString5(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return F1e;
			else if(col == 2)
				return F2e;
		}
		else
		{
			if(col == 4)
				return F3e;
			else if(col == 6)
				return F4e;
		}
		return "";
	}
	
	private String getWhiteKingString6(int col)
	{
		if(col < 4)
		{
			if(col ==1)
				return G1e;
			else if(col ==3)
				return G2e;
		}
		else
		{
			if(col ==5)
				return G3e;
			else if(col ==7)
				return G4e;
		}
		return "";
	}
	
	private String getWhiteKingString7(int col)
	{
		if(col < 3)
		{
			if(col == 0)
				return H1e;
			else if(col == 2)
				return H2e;
		}
		else
		{
			if(col == 4)
				return H3e;
			else if(col == 6)
				return H4e;
		}
		return "";
	}
}
