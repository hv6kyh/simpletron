package simpletron;

//import java.util.Scanner;

public class Simpletron {

	// 상수언선
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
	
	// 전역변수
	private int accumulator;			// 누산기
	private int [] memory;				// 명령어, 변수 담을 배열
	private int instructionRegister;	// 명령어 레지스터
	private int instructionCounter;		// 명령어 카운터
	private int opcode;					// 연산자
	private int operand;				// 피연산자
	
	private String result;
	
//	private Scanner sc;					// 스캐너 객체	
	
	
	public Simpletron () {
		
		// 생성자에서 초기화
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
			msg = "READ 명령 실행중";
			break;
			
		case WRITE:
			msg = "WRITE 명령 실행중";
			break;
			
		case LOAD:
			msg = "LOAD 명령 실행중";
			break;
			
		case STORE:
			msg = "STORE 명령 실행중";
			break;
			
		case ADD:
			msg = "ADD 명령 실행중";
			break;
			
		case SUBSTRACT:
			msg = "SUBSTRACT 명령 실행중";
			break;
			
		case DIVIDE:
			msg = "DIVIDE 명령 실행중";
			break;
			
		case MULTIPLY:
			msg = "MULTIPLY 명령 실행중";
			break;
			
		case BRANCH:
			msg = "BRANCH 명령 실행중";
			break;
			
		case BRANCHNEG:
			msg = "BRANCHNEG 명령 실행중";
			break;	
			
		case BRANCHZERO:
			msg = "BRANCHZERO 명령 실행중";
			break;
			
		case HALT:
			msg = "HALT 명령 실행중";
			break;			
			
			
		}
		
		System.out.println("------------------------------------------------");
		System.out.print(instructionCounter + "번째 명령어 ");
		System.out.println(msg);
		System.out.println("현재 메모리 주소 : " + operand);
		System.out.println("memory[" + operand + "] : " + memory[operand]);
		System.out.println("현재 누산기 값 : " + accumulator);
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
		
		int instruction = 0;	// 명령어
		int count = 0;
		int i = 0;				// split 반복자
		
		do {
			
//			System.out.printf ("%2d %s  ", count, "?" );
			
//			instruction = sc.nextInt ();	// 키보드로 입력
//			int tmp = Integer.parseInt( splitStr[i++] );
//			System.out.println(tmp);
//			instruction = tmp;
			
			// 잘못된 문자 입력시 종료
			try {
				instruction = Integer.parseInt( splitStr[i++] );
			} catch(NumberFormatException e) {
				return -1;
			} catch (Exception e) {
				// TODO: handle exception
				return -9;
			}
			
//			System.out.println("디버깅 중" + instruction);
			
			if ( instruction != -99999 ) {
				
				if ( isOverflow(instruction))
					return 0;
				else
					memory [ count++ ] = instruction;
			}
			
//			// 정상적인 경우 배열에 명령어 추가
//			if ( instruction != -99999 && !isOverflow(instruction))
//				memory [ count++ ] = instruction;		// 배열에 명령어 하나씩 저장
//			else if ( instruction != -99999 && isOverflow(instruction))
//				return 0;		// 오버플로우 발생시 종료
			
		} while( instruction != -99999 && count < memory.length );
		
	    
//	    System.out.println("*** Program loading completed ***");
//	    System.out.println("*** Program execution begins ***");
	    
		return 1;
	    
	}
	
	public String execute (String[] splitStr) {
		
//		String result = "";
		int c = 0;			// 토큰 인덱스
		int tmp = 0;		// 반복문 수행 횟수
		int tmpInst = 0;;
		
//		for (int i = 0; i < memory.length; i++) {
		do {
			
			// 범위를 벗어나면 바로 종료
			if ( isOverflow(accumulator) ) {
//				System.out.println("*** Simpletron execution abnomally terminated ***");
				result = "*** Simpletron execution abnomally terminated ***";
				
//				System.exit(0);
				return result;
				
			}
			
			// 명령어 fetch
			instructionRegister = memory[instructionCounter];
		
			// 연산자, 피연산자 분리
			opcode = instructionRegister / 100;
			operand = instructionRegister % 100;

			switch ( opcode ) {		// opcode의 종류에 따라 switch문으로 구성
			
				case READ: // 변수 입력
					
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
					
				case WRITE:	// 화면에 출력
					
					result += "result : " + memory [ operand ] + "\n";
					instructionCounter++;
					break;
					
				case LOAD: // 누산기로 가져옴
					
					accumulator = memory [ operand ];
					instructionCounter++;
					break;
					
				case STORE:	// 현재 누산기값 저장
					
					memory [ operand ] = accumulator;
					instructionCounter++;
					break;
					
				case ADD: // 현재 배열값을 누산기의 값과 더해 누산기에 저장
					
					accumulator += memory [ operand ];
					instructionCounter++;
					break;
					
				case SUBSTRACT: // 배열값을 누산기의 결과와 뺄셈하여 누산기에 저장
					
					accumulator -= memory [ operand ];
					instructionCounter++;
					break;
					
				case DIVIDE:	// 나눗셈 수행후 누산기에 저장
					
					// 런타임 오류시 바로 종료
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
					
				case MULTIPLY: // 곱셈 수행후 누산기에 저장
					
					accumulator *= memory [ operand ];
					instructionCounter++;
					break;
					
				case BRANCH:	// 임의 위치로 이동
					
					instructionCounter = operand;
					break;
					
				case BRANCHNEG:	// 누산기 값이 음이면 임의 위치로 이동
					
					if ( accumulator < 0 )
						instructionCounter = operand;
					else
						instructionCounter++;
					break;
					
				case BRANCHZERO:	// 누산기 값이 0이면 임의 위치로 이동
					
					if ( accumulator == 0 )
						instructionCounter = operand;
					else
						instructionCounter++;
					break;
					
				case HALT: 	// 프로그램 종료
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
		} while (opcode != HALT && tmp < memory.length);				// 명령어가 43이 나올때까지, 혹은 배열의 크기만큼 반복했을때
		
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