package simpletron;

//import java.util.Scanner;

public class Simpletron {

	// �����
	private final int READ = 10;
	private final int WRITE = 11;
	private final int LOAD = 20;
	private final int STORE = 21;
	private final int ADD = 30;
	private final int SUBSTRACT = 31;
	private final int DIVIDE = 32;
	private final int MULTIPLY = 33;
	private final int BRANCH = 40;
	private final int BRANCHNEG = 41;
	private final int BRANCHZERO = 42;
	private final int HALT = 43;
	
	// ��������
	private int accumulator;			// �����
	private int [] memory;				// ��ɾ�, ���� ���� �迭
	private int instructionRegister;	// ��ɾ� ��������
	private int instructionCounter;		// ��ɾ� ī����
	private int opcode;					// ������
	private int operand;				// �ǿ�����
	
	private String result;
	
//	private Scanner sc;					// ��ĳ�� ��ü	
	
	
	public Simpletron () {
		
		// �����ڿ��� �ʱ�ȭ
		memory = new int[100];
		accumulator = 0;
		instructionCounter = 0;
		instructionRegister = 0;
		opcode = 0;
		operand = 0;
		result = "";
		
//		sc = new Scanner(System.in);

	}
	
	public void showDebug() {	
		
		String msg = null;
		
		switch (opcode) {
		
		case READ:
			msg = "READ ��� ������";
			break;
			
		case WRITE:
			msg = "WRITE ��� ������";
			break;
			
		case LOAD:
			msg = "LOAD ��� ������";
			break;
			
		case STORE:
			msg = "STORE ��� ������";
			break;
			
		case ADD:
			msg = "ADD ��� ������";
			break;
			
		case SUBSTRACT:
			msg = "SUBSTRACT ��� ������";
			break;
			
		case DIVIDE:
			msg = "DIVIDE ��� ������";
			break;
			
		case MULTIPLY:
			msg = "MULTIPLY ��� ������";
			break;
			
		case BRANCH:
			msg = "BRANCH ��� ������";
			break;
			
		case BRANCHNEG:
			msg = "BRANCHNEG ��� ������";
			break;	
			
		case BRANCHZERO:
			msg = "BRANCHZERO ��� ������";
			break;
			
		case HALT:
			msg = "HALT ��� ������";
			break;			
			
			
		}
		
		System.out.println("------------------------------------------------");
		System.out.print(instructionCounter + "��° ��ɾ� ");
		System.out.println(msg);
		System.out.println("���� �޸� �ּ� : " + operand);
		System.out.println("memory[" + operand + "] : " + memory[operand]);
		System.out.println("���� ����� �� : " + accumulator);
		System.out.println("------------------------------------------------");
		
	}
	

	public void displayMessage ( ) {
		
		System.out.println("*** Welcome to Simpletron! ***");
		System.out.println("*** Please enter your program one istruction ***");
		System.out.println("*** (or data word) at a time. I will type the ***");
		System.out.println("*** location number and a question mark (?) ***");
		System.out.println("*** You then type the word for that location. ***");
		System.out.println("*** Type the sectinel -99999 to stop entering ***");
		System.out.println("*** your program ****");		
		
	}
	
	public boolean isOverflow(int num) {
		
		if (num > 9999 || num < -9999)
			return true;
		
		return false;
		
	}
	
	public int inputInstruction(String[] splitStr) {
		
		int instruction = 0;	// ��ɾ�
		int count = 0;
		int i = 0;				// split �ݺ���
		
		do {
			
//			System.out.printf ("%2d %s  ", count, "?" );
			
//			instruction = sc.nextInt ();	// Ű����� �Է�
//			int tmp = Integer.parseInt( splitStr[i++] );
//			System.out.println(tmp);
//			instruction = tmp;
			
			// �߸��� ���� �Է½� ����
			try {
				instruction = Integer.parseInt( splitStr[i++] );
			} catch(NumberFormatException e) {
				return -1;
			} catch (Exception e) {
				// TODO: handle exception
				return -9;
			}
			
//			System.out.println("����� ��" + instruction);
			
			if ( instruction != -99999 ) {
				
				if ( isOverflow(instruction))
					return 0;
				else
					memory [ count++ ] = instruction;
			}
			
//			// �������� ��� �迭�� ��ɾ� �߰�
//			if ( instruction != -99999 && !isOverflow(instruction))
//				memory [ count++ ] = instruction;		// �迭�� ��ɾ� �ϳ��� ����
//			else if ( instruction != -99999 && isOverflow(instruction))
//				return 0;		// �����÷ο� �߻��� ����
			
		} while( instruction != -99999 && count < memory.length );
		
	    
//	    System.out.println("*** Program loading completed ***");
//	    System.out.println("*** Program execution begins ***");
	    
		return 1;
	    
	}
	
	public String execute (String[] splitStr) {
		
//		String result = "";
		int c = 0;			// ��ū �ε���
		int tmp = 0;		// �ݺ��� ���� Ƚ��
		int tmpInst = 0;;
		
//		for (int i = 0; i < memory.length; i++) {
		do {
			
			// ������ ����� �ٷ� ����
			if ( isOverflow(accumulator) ) {
//				System.out.println("*** Simpletron execution abnomally terminated ***");
				result = "*** Simpletron execution abnomally terminated ***";
				
//				System.exit(0);
				return result;
				
			}
			
			// ��ɾ� fetch
			instructionRegister = memory[instructionCounter];
		
			// ������, �ǿ����� �и�
			opcode = instructionRegister / 100;
			operand = instructionRegister % 100;

			switch ( opcode ) {		// opcode�� ������ ���� switch������ ����
			
				case READ: // ���� �Է�
					
					try {
						tmpInst = Integer.parseInt( splitStr[c++] );
					} catch (NumberFormatException e) {
						result = "*** An invalid value was entered. ***";
						return result;
					} catch (Exception e) {
						// TODO: handle exception
						result = "*** An unexpected error has occurred ***";
						return result;
					}
					
//					System.out.print ( "input number : " );
//					memory [ operand ] = sc.nextInt ();
//					memory [ operand ] = Integer.parseInt( splitStr[c++] );
					memory [ operand ] = tmpInst;
					
					instructionCounter++;
					break;
					
				case WRITE:	// ȭ�鿡 ���
					
					result += "result : " + memory [ operand ] + "\n";
					instructionCounter++;
					break;
					
				case LOAD: // ������ ������
					
					accumulator = memory [ operand ];
					instructionCounter++;
					break;
					
				case STORE:	// ���� ����Ⱚ ����
					
					memory [ operand ] = accumulator;
					instructionCounter++;
					break;
					
				case ADD: // ���� �迭���� ������� ���� ���� ����⿡ ����
					
					accumulator += memory [ operand ];
					instructionCounter++;
					break;
					
				case SUBSTRACT: // �迭���� ������� ����� �����Ͽ� ����⿡ ����
					
					accumulator -= memory [ operand ];
					instructionCounter++;
					break;
					
				case DIVIDE:	// ������ ������ ����⿡ ����
					
					// ��Ÿ�� ������ �ٷ� ����
					if (memory[operand] == 0) {
//						System.out.println("*** Attempt to divide by zero ***");
						result = "*** Attempt to divide by zero ***";
						
//						System.exit(0);
						return result;
						
					}
					else {
						accumulator /=  memory [ operand ];
						instructionCounter++;
						break;
					}
					
				case MULTIPLY: // ���� ������ ����⿡ ����
					
					accumulator *= memory [ operand ];
					instructionCounter++;
					break;
					
				case BRANCH:	// ���� ��ġ�� �̵�
					
					instructionCounter = operand;
					break;
					
				case BRANCHNEG:	// ����� ���� ���̸� ���� ��ġ�� �̵�
					
					if ( accumulator < 0 )
						instructionCounter = operand;
					else
						instructionCounter++;
					break;
					
				case BRANCHZERO:	// ����� ���� 0�̸� ���� ��ġ�� �̵�
					
					if ( accumulator == 0 )
						instructionCounter = operand;
					else
						instructionCounter++;
					break;
					
				case HALT: 	// ���α׷� ����
					//showDump ();
//					result += addDump();
//					addDump();
					result += "*** Simpletron execution terminated ***" + "\n";
//					System.exit ( 0 );
					break;
	
			}
			
			tmp++;
			
			System.out.println("opcode = " + opcode);
			
//		}
		} while (opcode != HALT && tmp < memory.length);				// ��ɾ 43�� ���ö�����, Ȥ�� �迭�� ũ�⸸ŭ �ݺ�������
		
		addDump();
		return result;

	}

	public void addDump () {

		result += String.format ("\r\n%30s\r\n", "REGISTERS :") ;
		result += String.format ("%30s \t %4d\n", "accumulator", accumulator);
		result += String.format ("%30s \t %4d\n", "instructionCounter", instructionCounter);
		result += String.format ("%30s \t %4d\n", "instructionRegister", instructionRegister);
		result += String.format ("%30s \t %4d\n", "operationCode", opcode);
		result += String.format ("%30s \t %4d\n\n", "operand", operand);
		


		for ( int i = 0; i < 10; i++ )
			result += String.format  ( "%10d", i);
		


		result += "\n";
		
		
		int counter = 0;

		for (int i = 0; i < 10; i++ ) {
			
			if ( counter %10 == 0 )
				result += String.format  ("%2d ", counter);
			
			for (int j = 0; j < 10; j++) {	
				
				if ( memory [ counter ] == 0 )
					result += String.format  ( "%s%s", "+", "0000 ");
				else {
					
					if (memory[counter] < 100) {
						
						String str = "00" + memory[counter];
						
					
						result += String.format  ("%s%s ", "+", str);
					
					}
					else
						result += String.format  ("%s%4d ", "+", memory[counter]);
				}
				counter++;

			}
		       
		result += "\n";	

		}
		return;
		
	}

}