import java.io.RandomAccessFile;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class DavisBase {

	/* This can be changed to whatever you like */
	static String prompt = "Davissql> ";
	static String version = "v1.0a(Final_Project_CS6360)";
	static String copyright = "©2018 Chris Irwin Davis, Created by group J, Fall 2018 - Heta(hxs180029),Arun(axs170081),Anant(axv180022),Kanika(kxr180008)";
	static boolean isExit = false;

	/*
	 * Page size for all files is 512 bytes by default. You may choose to make it
	 * user modifiable
	 */
	static long pageSize = 512;
	static long pageSz = pageSize;
	static long pageSizeCol = 2048;
	static long pageSzC = pageSizeCol;
	static long pageSz_col = pageSize;
	static long pageSzI = pageSize;

	// CONSTANTS
	static final String CATALOG_PATH = "./data-repository/catalog/";
	static final String USER_DATA_PATH = "./data-repository/user-data/";
	static final String DAVISBASE_TABLES = "davisbase_tables";
	static final String DAVISBASE_COLUMNS = "davisbase_columns";
	static final String TABLE_EXTENSION = ".tbl";
	static final String INDEX_EXTENSION = ".ndx";
	static final String MODE_RW = "rw";
	static final String NO = "NO";
	static final String TAB_SEPARATOR = "\t";
	static final String FORWARDSLASH = "/";
	static final String SEMI_COLON = ";";
	static final String HYPHEN = "-";

	String tableName = null;
	RandomAccessFile catalog;
	RandomAccessFile user_data;
	static int pk_occurence = 0;
	/*
	 * The Scanner class is used to collect user commands from the prompt There are
	 * many ways to do this. This is just one.
	 *
	 * Each time the semicolon (;) delimiter is entered, the userCommand String is
	 * re-populated.
	 */
	static Scanner scanner = new Scanner(System.in).useDelimiter(SEMI_COLON);

	/**
	 * *********************************************************************** Main
	 * method
	 */
	public static void main(String[] args) {
		// change later
		File dataRepoP = new File("./data-repository");
		File mi = new File(CATALOG_PATH);
		File tr = new File(USER_DATA_PATH);

		if (!dataRepoP.exists()) {
			/* Display the welcome screen */
			splashScreen();

			System.out.println("Initialising DataBase...........");
			boolean res = false;
			try {
				dataRepoP.mkdir();
				mi.mkdir();

				RandomAccessFile catalog = new RandomAccessFile(CATALOG_PATH + DAVISBASE_TABLES + TABLE_EXTENSION,
						MODE_RW);
				catalog.setLength(pageSize);
				RandomAccessFile user_data = new RandomAccessFile(CATALOG_PATH + DAVISBASE_COLUMNS + TABLE_EXTENSION,
						MODE_RW);
				user_data.setLength(pageSizeCol);
				int lenTab = DAVISBASE_TABLES.length();
				int lenCol = DAVISBASE_COLUMNS.length();

				pageSz = pageSz - lenTab - 1;
				catalog.seek(pageSz);
				catalog.writeByte(1);
				catalog.writeBytes(DAVISBASE_TABLES);

				pageSz = pageSz - lenCol - 1;
				catalog.seek(pageSz);
				catalog.writeByte(2);
				catalog.writeBytes(DAVISBASE_COLUMNS);

				catalog.seek(1);
				catalog.writeByte(02);

				catalog.seek(2);
				catalog.writeShort(477);

				catalog.seek(8);
				catalog.writeShort(495);

				catalog.seek(10);
				catalog.writeShort(477);

				user_data.setLength(pageSizeCol);
				user_data.seek(8);
				user_data.writeShort((int) (pageSizeCol - 34));
				user_data.seek(pageSizeCol - 34);

				user_data.writeByte(1);
				user_data.writeByte(1);

				user_data.writeByte(16);
				user_data.writeBytes(DAVISBASE_TABLES);

				user_data.writeByte(5);
				user_data.writeBytes("rowid");

				user_data.writeByte(3);
				user_data.writeBytes("INT");

				user_data.writeByte(1);
				user_data.writeByte(1);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 34));

				pageSizeCol = pageSizeCol - 34;
				user_data.seek(10);
				user_data.writeShort((int) (pageSizeCol - 40));
				user_data.seek(pageSizeCol - 40);

				user_data.writeByte(1);
				user_data.writeByte(2);

				user_data.writeByte(16);
				user_data.writeBytes(DAVISBASE_TABLES);

				user_data.writeByte(10);
				user_data.writeBytes("table_name");

				user_data.writeByte(4);
				user_data.writeBytes("TEXT");

				user_data.writeByte(1);
				user_data.writeByte(2);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 40));

				pageSizeCol = pageSizeCol - 40;
				user_data.seek(12);
				user_data.writeShort((int) (pageSizeCol - 35));
				user_data.seek(pageSizeCol - 35);

				user_data.writeByte(1);
				user_data.writeByte(3);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(5);
				user_data.writeBytes("rowid");

				user_data.writeByte(3);
				user_data.writeBytes("INT");

				user_data.writeByte(1);
				user_data.writeByte(1);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 35));

				pageSizeCol = pageSizeCol - 35;
				user_data.seek(14);
				user_data.writeShort((int) (pageSizeCol - 41));
				user_data.seek(pageSizeCol - 41);

				user_data.writeByte(1);
				user_data.writeByte(4);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(10);
				user_data.writeBytes("table_name");

				user_data.writeByte(4);
				user_data.writeBytes("TEXT");

				user_data.writeByte(1);
				user_data.writeByte(2);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 41));

				pageSizeCol = pageSizeCol - 41;
				user_data.seek(16);
				user_data.writeShort((int) (pageSizeCol - 42));
				user_data.seek(pageSizeCol - 42);

				user_data.writeByte(1);
				user_data.writeByte(4);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(11);
				user_data.writeBytes("column_name");

				user_data.writeByte(4);
				user_data.writeBytes("TEXT");

				user_data.writeByte(1);
				user_data.writeByte(3);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 42));

				pageSizeCol = pageSizeCol - 42;
				user_data.seek(18);
				user_data.writeShort((int) (pageSizeCol - 40));
				user_data.seek(pageSizeCol - 40);

				user_data.writeByte(1);
				user_data.writeByte(6);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(9);
				user_data.writeBytes("data_type");

				user_data.writeByte(4);
				user_data.writeBytes("TEXT");

				user_data.writeByte(1);
				user_data.writeByte(4);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 40));

				pageSizeCol = pageSizeCol - 40;
				user_data.seek(20);
				user_data.writeShort((int) (pageSizeCol - 50));
				user_data.seek(pageSizeCol - 50);

				user_data.writeByte(1);
				user_data.writeByte(7);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(16);
				user_data.writeBytes("ColumnOrderition");

				user_data.writeByte(7);
				user_data.writeBytes("TINYINT");

				user_data.writeByte(1);
				user_data.writeByte(5);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 50));

				pageSizeCol = pageSizeCol - 50;
				user_data.seek(22);
				user_data.writeShort((int) (pageSizeCol - 42));
				user_data.seek(pageSizeCol - 42);

				user_data.writeByte(1);
				user_data.writeByte(8);

				user_data.writeByte(17);
				user_data.writeBytes(DAVISBASE_COLUMNS);

				user_data.writeByte(11);
				user_data.writeBytes("is_nullable");

				user_data.writeByte(4);
				user_data.writeBytes("TEXT");

				user_data.writeByte(1);
				user_data.writeByte(5);

				user_data.writeByte(2);
				user_data.writeBytes(NO);

				user_data.seek(2);
				user_data.writeShort((int) (pageSizeCol - 42));
				tr.mkdir();
				res = true;

				user_data.seek(1);
				user_data.writeByte(8);

				catalog.close();
				user_data.close();
			} catch (Exception e) {
				Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, e);
				System.out.println(e.getMessage());
			}
			if (res) {
				System.out.println("Database \"davisbase\" intialised...... ");
			}
		} else {
			/* Display the welcome screen */
			splashScreen();
			System.out.println("Database selected: davisbase");
		}

		/* Variable to collect user input from the prompt */
		String userCommand = "";

		while (!isExit) {
			System.out.print(prompt);
			/* toLowerCase() renders command case insensitive */
			userCommand = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
			parseUserCommand(userCommand);
		}
		System.out.println("Exiting...");

	}

	public static void splashScreen() {
		System.out.println(line(HYPHEN, 80));
		System.out.println("Welcome to DavisBaseLite");
		System.out.println("DavisBaseLite Version " + getVersion());
		System.out.println(getCopyright());
		System.out.println("\nType \"help;\" to display supported commands.");
		System.out.println(line(HYPHEN, 80));
	}

	/**
	 * @param string The String to be repeated
	 * @param num    The number of time to repeat String string.
	 * @return String retString object, which is the String s appended to itself num
	 *         times.
	 */
	public static String line(String string, int num) {
		String restString = "";
		for (int i = 0; i < num; i++) {
			restString += string;
		}
		return restString;
	}

	/**
	 * Help: Display supported commands
	 */
	public static void help() {
		System.out.println(line("*", 80));
		System.out.println("SUPPORTED COMMANDS");
		System.out.println("All commands below are case insensitive");
		System.out.println();
		System.out.println("\tSHOW TABLES;");
		System.out.println(
				"\tCREATE TABLE table_name (<col_name1> <col_type> primary key, <col_name2> <col_type> not null, <col_name3> <col_type>..);");
		System.out.println("\tINSERT INTO table_name (column_list) VALUES (value1,value2,value3,...);");
		System.out.println("\tUPDATE table_name SET col_name = value WHERE col_name = value;");
		System.out.println("\tDELETE FROM TABLE table_name WHERE row_id = value;");
		System.out.println(
				"\tSELECT * FROM columns;                           Display all columns from all table of the database.");
		System.out.println("\tSELECT * FROM table_name;                        Display all records in the table.");
		System.out.println("\tSELECT * FROM table_name WHERE row_id = <value>; Display records whose rowid is <id>.");
		System.out.println("\tDROP TABLE table_name;                           Remove table data and its schema.");
		System.out.println("\tVERSION;                                         Show the program version.");
		System.out.println("\tHELP;                                            Show this help information");
		System.out.println("\tEXIT;                                            Exit the program");
		System.out.println();
		System.out.println();
		System.out.println(line("*", 80));
	}

	/** return the DavisBase version */
	public static String getVersion() {
		return version;
	}

	public static String getCopyright() {
		return copyright;
	}

	public static void displayVersion() {
		System.out.println("DavisBaseLite Version " + getVersion());
		System.out.println(getCopyright());
	}

	public static void sortLeafPage(RandomAccessFile file, int page) {
		try {
			long length = file.length();
			int newpage = (int) (length / 512 + 1);
			file.setLength(newpage * 512);
			file.seek(512 * (newpage - 1));
			file.write(0x0D);
			file.write(0);
			file.writeShort(512);
			file.seek(512 * (page - 1) + 1);
			int num = file.readByte();
			for (int i = 1; i <= num; i++) {
				moveLeafRow(i, file, page, newpage);
			}
			file.seek(512 * (page - 1));
			file.write(0x0D);
			file.write(0);
			file.writeShort(512);
			for (int i = 1; i <= num; i++) {
				moveLeafRow(i, file, newpage, page);
			}
			file.setLength((newpage - 1) * 512);
		} catch (Exception e) {
			System.out.println("Unable to sort the page");
			System.out.println(e);
		}
	}

	public static void moveLeafRow(int rowNum, RandomAccessFile file, int oldPage, int newPage) {
		try {
			file.seek(512 * (oldPage - 1) + 2 + 2 * rowNum);
			int offSet1 = file.readShort();
			file.seek(offSet1 + 512 * (oldPage - 1));
			int payload = file.readShort();
			file.seek(offSet1 + 6 + 512 * (oldPage - 1));
			int attributes = file.read();
			int size = payload + attributes + 3;
			file.seek(512 * (newPage - 1) + 1);
			int num = file.read();
			file.seek(512 * (newPage - 1) + 1);
			file.write(num + 1);
			file.seek(512 * (newPage - 1) + 2);
			int offSet2 = file.readShort();
			offSet2 = offSet2 - size;
			file.seek(512 * (newPage - 1) + 2);
			file.writeShort(offSet2);
			file.seek(512 * (newPage - 1) + 4 + 2 * num);
			file.writeShort(offSet2);
			for (int i = 0; i < size; i++) {
				file.seek(offSet1 + i + 512 * (oldPage - 1));
				Byte value = file.readByte();
				file.seek(offSet2 + i + 512 * (newPage - 1));
				file.writeByte(value);
			}
		} catch (Exception e) {
			System.out.println("Unable to move row to the new leaf page");
			System.out.println(e);
		}
	}

	public static int findParent(RandomAccessFile file, int page, int rootPage) {
		try {
			file.seek(512 * (page - 1));
			byte type = file.readByte();
			file.read();
			int offSet = file.readShort();
			int rowId = -1;
			if (type == 0x0D) {
				file.seek(offSet + 2 + 512 * (page - 1));
				rowId = file.readInt();
			} else {
				file.seek(offSet + 4 + 512 * (page - 1));
				rowId = file.readInt();
			}
			int no = 0;
			int pageNum = 0;
			file.seek(512 * (rootPage - 1) + 1);
			int number = file.read();
			for (int i = 1; i <= number; i++) {
				file.seek(512 * (rootPage - 1) + 2 * i + 6);
				offSet = file.readShort();
				file.seek(offSet + 4 + 512 * (rootPage - 1));
				int key = file.readInt();
				if (rowId < key) {
					no = i;
					file.seek(offSet + 512 * (rootPage - 1));
					pageNum = file.readInt();
					if (pageNum == page)
						return rootPage;
					else
						break;
				}

			}
			if (no == 0) {
				file.seek(512 * (rootPage - 1) + 4);
				pageNum = file.readInt();
				if (pageNum == page)
					return rootPage;
				else
					return findParent(file, page, pageNum);
			} else {
				return findParent(file, page, pageNum);
			}
		} catch (Exception e) {
			System.out.println("Unable to find the Parent");
			System.out.println(e);
			return -1;
		}
	}

	public static int findPage(RandomAccessFile file, int rowId, int page) {
		try {
			file.seek(512 * (page - 1));
			byte type = file.readByte();
			if (type == 0x0D) {
				return page;
			} else {
				file.seek(512 * (page - 1) + 1);
				int number = file.readByte();
				int no = -1;
				for (int i = 1; i <= number; i++) {
					file.seek(512 * (page - 1) + 2 * i + 6);
					int offset = file.readShort();
					file.seek(offset + 4 + 512 * (page - 1));
					int key = file.readInt();
					if (key > rowId) {
						no = i;
						break;
					}
				}
				if (no != -1) {
					file.seek(512 * (page - 1) + 2 * no + 6);
					int offSet = file.readShort();
					file.seek(offSet + 512 * (page - 1));
					int pageNum = file.readInt();
					int a = findPage(file, rowId, pageNum);
					return a;
				} else {
					file.seek(512 * (page - 1) + 4);
					int pageNum = file.readInt();
					int pageF = findPage(file, rowId, pageNum);
					return pageF;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to find the page");
			System.out.println(e);
			return -1;
		}
	}

	public static int findNoOfInternalLeaf(RandomAccessFile file, int rowId, int page) {
		try {
			file.seek(512 * (page - 1) + 1);
			int no = -1;
			int number = file.readByte();
			for (int i = 1; i <= number; i++) {
				file.seek(512 * (page - 1) + 2 + 2 * i);
				int offset = file.readShort();
				file.seek(offset + 2 + 512 * (page - 1));
				int key = file.readInt();
				if (key == rowId)
					no = i;
			}
			return no;
		} catch (Exception e) {
			System.out.println("Unable to find no in the leaf");
			System.out.println(e);
			return -1;
		}
	}

	public static void parseUserCommand(String userCommand) {

		/*
		 * commandTokens is an array of Strings that contains one token per array
		 * element The first token can be used to determine the type of command The
		 * other tokens can be used to pass relevant parameters to each command-specific
		 * method inside each case statement
		 */

		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(userCommand.split(" ")));

		/*
		 * This switch handles a very small list of hard coded commands of known syntax.
		 * You will want to rewrite this method to interpret more complex commands.
		 */
		switch (commandTokens.get(0)) {
		case "select":
			if (commandTokens.get(3).equalsIgnoreCase("columns"))
				parseColumnSelectQuery(userCommand);
			else
				parseSelectQuery(userCommand);
			break;
		case "show":
			parseShowQuery(userCommand);
			break;
		case "insert":
			parseInsertQuery(userCommand);
			break;
		case "drop":
			dropTable(userCommand);
			break;
		case "delete":
			dropTable(userCommand);
			break;
		case "create":
			parseCreateQuery(userCommand);
			break;
		case "update":
			parseUpdateQuery(userCommand);
			break;
		case "help":
			help();
			break;
		case "version":
			displayVersion();
			break;
		case "exit":
			isExit = true;
			break;
		case "quit":
			isExit = true;
		default:
			System.out.println("Command not recognised : \"" + userCommand + "\"");
			System.out.println("Try \"help;\" for more information.");
			break;
		}
	}

	/**
	 * Stub method for dropping tables
	 * 
	 * @param dropTableString is a String of the user input
	 */

	public static void parseColumnSelectQuery(String userCommand) {
		ArrayList<Character> selectDisplayRowData = new ArrayList<>();
		try {
			RandomAccessFile binaryQueFile = new RandomAccessFile(CATALOG_PATH + DAVISBASE_COLUMNS + TABLE_EXTENSION,
					MODE_RW);
			int StratAdd = 0x08;
			binaryQueFile.seek(StratAdd);
			int tempShortByte = binaryQueFile.readShort();
			while (tempShortByte != 0) {
				binaryQueFile.seek(StratAdd);
				tempShortByte = binaryQueFile.readShort();
				StratAdd += 2;
				binaryQueFile.seek(++tempShortByte);
				int seekRLength = tempShortByte + 1;
				binaryQueFile.seek(seekRLength);
				int firstLength = binaryQueFile.read();
				for (int i = 0; i < firstLength; i++) {
					binaryQueFile.seek(++seekRLength);
					char CharVal = (char) (binaryQueFile.read());
					selectDisplayRowData.add(CharVal);
				}
				int setRLength = seekRLength + 1;
				selectDisplayRowData.add(' ');
				for (int k = 2; k < 6; k++) {
					binaryQueFile.seek(setRLength);
					int rLength = binaryQueFile.read();
					for (int i = 0; i < rLength; i++) {
						setRLength += 1;
						binaryQueFile.seek(setRLength);
						char charVal = (char) (binaryQueFile.read());
						selectDisplayRowData.add(charVal);
					}
					selectDisplayRowData.add(' ');
					setRLength += 1;
					rLength = binaryQueFile.read();
				}
				selectDisplayRowData.add(';');
				binaryQueFile.seek(StratAdd - 2);
				tempShortByte = binaryQueFile.read();
			}
			String finalData = "";
			for (int j = 0; j < selectDisplayRowData.size(); j++) {
				finalData = finalData + selectDisplayRowData.get(j);
			}
			String rowData[] = finalData.split(SEMI_COLON);
			String[] key;
			ArrayList<String> columns = new ArrayList<>();
			for (int k = 0; k < rowData.length - 1; k++) {
				key = rowData[k].split(" ");
				for (int j = 0; j < key.length; j++) {
					columns.add(key[j]);
				}
			}
			int x1 = 0;
			int x2 = 1;
			ArrayList<Integer> y1 = new ArrayList<>();
			ArrayList<String> y2 = new ArrayList<>();
			String tempString = null;
			for (int i = 0; i < columns.size(); i += 5) {
				tempString = columns.get(i);
				if (!y2.contains(tempString))
					y2.add(tempString);
			}
			for (int j = 0; j < y2.size(); j++) {
				int count = 0;
				for (int i = 0; i < columns.size(); i += 5) {
					if ((columns.get(i).equalsIgnoreCase(y2.get(j)))) {
						count++;
						y1.add(count);
					}
				}
			}
			int z1 = 3;
			for (int ray = 0; ray < columns.size() / 5; ray++) {
				columns.set(z1, y1.get(ray).toString());
				z1 += 5;
			}
			ArrayList<String> headings = new ArrayList<String>();
			headings.add("row_id");
			headings.add("table_name");
			headings.add("column_name");
			headings.add("data_type");
			headings.add("ColumnOrderition");
			headings.add("is_nullable");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < headings.size(); i++) {
				System.out.format("%-20s", headings.get(i));
			}
			System.out.println();
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < columns.size() / 5; i++) {
				System.out.format("%-20s", x2);
				for (int j = x1; j < x1 + 5; j++) {
					System.out.format("%-20s", columns.get(j));
				}
				System.out.println();
				x2++;
				x1 += 5;
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------");

			binaryQueFile.close();

		} catch (Exception e) {
			Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}

	public static void dropTable(String dropTableString) {
		try {
			ArrayList<String> queryStringTokens = new ArrayList<String>(Arrays.asList(dropTableString.split(" ")));
			int rowNo = 0;
			String tableName = null;
			if (queryStringTokens.size() > 3) {
				tableName = queryStringTokens.get(3);
				rowNo = Integer.parseInt(queryStringTokens.get(7));
			} else {
				tableName = queryStringTokens.get(2);
			}

			if (queryStringTokens.get(0).equalsIgnoreCase("delete")) {
				RandomAccessFile binaryQueFile2 = new RandomAccessFile(
						USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
				int StratAdd, result;
				int matchId = 0;
				int StratAddress = 0x08;
				binaryQueFile2.seek(StratAddress);
				while (binaryQueFile2.readShort() != 0) {
					binaryQueFile2.seek(StratAddress);
					if (-1 == binaryQueFile2.readShort()) {
					} else {
						binaryQueFile2.seek(StratAddress);
						result = (int) binaryQueFile2.readShort();
						result += 4;
						binaryQueFile2.seek(result);
						int recVal = binaryQueFile2.read();
						if (recVal == rowNo) {
							matchId = result - 4;
							StratAdd = 8;
							while (binaryQueFile2.readShort() != 0) {
								binaryQueFile2.seek(StratAdd);
								result = binaryQueFile2.readShort();
								if (result == matchId) {
									System.out.println("Record Deleted");
									binaryQueFile2.seek(StratAddress);
									binaryQueFile2.writeShort(0xFFFF);
									break;
								}
								StratAdd += 2;
							}
						}
					}
					StratAddress += 2;
				}
				binaryQueFile2.close();
			} else if (queryStringTokens.get(0).equalsIgnoreCase("drop")) {
				String a = USER_DATA_PATH + tableName;
				File s = new File(a);
				if (s.exists()) {
					deleteDir(s);
					RandomAccessFile binaryQueFile = new RandomAccessFile(
							CATALOG_PATH + DAVISBASE_TABLES + TABLE_EXTENSION, MODE_RW);
					int StratAdd = 8;
					int next = 6;
					int length;
					int selectValue, nextValue;
					binaryQueFile.seek(StratAdd);
					while (binaryQueFile.readShort() != 0) {
						String str = "";
						binaryQueFile.seek(StratAdd);
						selectValue = binaryQueFile.readShort();
						if (selectValue == -1) {
						} else {
							selectValue++;
							binaryQueFile.seek(next);
							nextValue = binaryQueFile.readShort();
							if (nextValue == 0) {
								nextValue = 512;
							}
							length = nextValue - selectValue;
							binaryQueFile.seek(selectValue);
							ArrayList<Character> SelectDisplayRowData = new ArrayList<>();
							for (int i = 0; i < length; i++) {
								binaryQueFile.seek(selectValue++);
								char CharVal = (char) (binaryQueFile.read());
								SelectDisplayRowData.add(CharVal);
							}
							for (int j = 0; j < SelectDisplayRowData.size(); j++) {
								str = str + SelectDisplayRowData.get(j);
							}
							if (str.equals(tableName)) {
								binaryQueFile.seek(StratAdd);
								binaryQueFile.writeShort(0xFFFF);
								break;
							}
						}
						StratAdd += 2;
						next += 2;
						binaryQueFile.seek(StratAdd);
					}
					System.out.println("Table dropped..");
					binaryQueFile.close();
				}

				else {
					System.out.println("Table Doesn't Exist, please check your command!");
				}
			}
		} catch (Exception ex) {
			System.out.println("Table Doesn't Exist, please check your command!");
		}
	}

	/**
	 * Stub method for executing queries
	 * 
	 * @param queryString is a String of the user input
	 */
	private static void parseUpdateQuery(String updateString) {
		try {
			ArrayList<String> updateStringTokens = new ArrayList<String>(Arrays.asList(updateString.split(" ")));
			String tbl = updateStringTokens.get(1);
			String updateColumnName = updateStringTokens.get(3);
			String updateColumnValue = updateStringTokens.get(5);
			String criteriaCoulmnName = updateStringTokens.get(7);
			String criteriaCoulmnValue = updateStringTokens.get(9);
			ArrayList<String> attributeOrder = new ArrayList<>();
			ArrayList<String> colNameList = new ArrayList<String>();
			File tableMetaFolder = new File(USER_DATA_PATH + tbl);
			File[] fileNames = tableMetaFolder.listFiles();
			for (int i = 0; i < fileNames.length; i++) {
				if (fileNames[i].isFile()) {
					String columnNdxName = fileNames[i].getName();
					if (columnNdxName.contains(INDEX_EXTENSION)) {
						colNameList.add(columnNdxName.substring(0, columnNdxName.indexOf(".")));
					}
				}
			}
			for (int i = 0; i < colNameList.size() + 15; i++) {
				attributeOrder.add(".");
			}

			for (int j = 0; j < colNameList.size(); j++) {
				RandomAccessFile indexFile = new RandomAccessFile(
						USER_DATA_PATH + tbl + FORWARDSLASH + colNameList.get(j) + INDEX_EXTENSION, MODE_RW);
				indexFile.seek(2);
				int position = indexFile.read();
				attributeOrder.set(position, colNameList.get(j));
				indexFile.close();
			}
			int selectAttributeIndex = attributeOrder.indexOf(criteriaCoulmnName);
			int attOrderIndex = attributeOrder.indexOf(updateColumnName);
			RandomAccessFile binaryQueFile = new RandomAccessFile(
					USER_DATA_PATH + tbl + FORWARDSLASH + tbl + TABLE_EXTENSION, MODE_RW);
			ArrayList<Character> selectDispRowData = new ArrayList<>();
			int startAddress = 8;
			binaryQueFile.seek(startAddress);
			int readValue = binaryQueFile.readShort();
			while (readValue != 0) {
				if (-1 == readValue) {
					startAddress = startAddress + 2;
					binaryQueFile.seek(startAddress);
					readValue = binaryQueFile.readShort();
				} else {
					binaryQueFile.seek(startAddress);
					readValue = binaryQueFile.readShort();
					binaryQueFile.seek(readValue);
					binaryQueFile.seek(readValue + 5);
					int numOfCol = binaryQueFile.read();
					int seekRLength = readValue + 5 + numOfCol + 1;
					binaryQueFile.seek(seekRLength);
					int firstLength = binaryQueFile.read();
					for (int i = 0; i < firstLength; i++) {
						binaryQueFile.seek(seekRLength + 1);
						char charVal = (char) (binaryQueFile.read());
						selectDispRowData.add(charVal);
					}
					int StrLength = seekRLength + firstLength + 1;
					selectDispRowData.add('`');
					for (int k = 1; k < numOfCol; k++) {
						binaryQueFile.seek(StrLength);
						int rLength = binaryQueFile.read();
						for (int i = 0; i < rLength; i++) {
							StrLength += 1;
							binaryQueFile.seek(StrLength);
							char charVal = (char) (binaryQueFile.read());
							selectDispRowData.add(charVal);
						}
						StrLength += 1;
						selectDispRowData.add('`');
					}
					startAddress = startAddress + 2;
					selectDispRowData.add(';');
					binaryQueFile.seek(startAddress);
				}
			}
			String finalData = "";
			for (int j = 0; j < selectDispRowData.size(); j++) {
				finalData = finalData + selectDispRowData.get(j);
			}
			ArrayList<String> rowDataList = new ArrayList<String>(Arrays.asList(finalData.split(SEMI_COLON)));
			ArrayList<String> selectColumn = new ArrayList<>();
			for (int k = 0; k < rowDataList.size() - 1; k++) {
				String column_extract[] = rowDataList.get(k).split("`");
				String selectDataAttributeWise = column_extract[selectAttributeIndex];
				selectColumn.add(selectDataAttributeWise);
			}
			ArrayList<Integer> recNo = new ArrayList<>();
			int temp = 1;
			for (int i = 0; i < selectColumn.size(); i++) {
				if (selectColumn.get(i).equals(criteriaCoulmnValue)) {
					recNo.add(temp);
				}
				temp++;
			}
			int toUpdate = 0;
			for (int i = 0; i < selectColumn.size(); i++) {
				if (selectColumn.get(i).equalsIgnoreCase(criteriaCoulmnValue))
					toUpdate = i;
			}
			ArrayList<String> selectCoulmnAttributeWise = new ArrayList<>();
			for (int k = 0; k < rowDataList.size() - 1; k++) {
				String columnExtract[] = rowDataList.get(k).split("`");
				String selectDataAttributeWise = columnExtract[attOrderIndex];
				selectCoulmnAttributeWise.add(selectDataAttributeWise);
			}
			selectCoulmnAttributeWise.set(toUpdate, updateColumnValue);
			int j = 0;
			while (j < recNo.size()) {
				startAddress = 0x08;
				int result = 0;
				binaryQueFile.seek(startAddress);
				int temp2 = binaryQueFile.readShort();
				while (temp2 != 0) {
					if (-1 == temp2) {
						startAddress = startAddress + 2;
						binaryQueFile.seek(startAddress);
						temp2 = binaryQueFile.readShort();
					} else {
						binaryQueFile.seek(startAddress);
						result = binaryQueFile.readShort();
						result += 4;
						binaryQueFile.seek(result);
						int recVal = binaryQueFile.read();
						int counter = 1;
						if (recVal == recNo.get(j)) {
							j++;
							binaryQueFile.seek(result + 1);
							int nCol = binaryQueFile.read();
							int toSeek = result + 1 + nCol + 1;
							binaryQueFile.seek(toSeek);
							int rLength = binaryQueFile.read();
							while (counter != attOrderIndex + 1) {
								binaryQueFile.seek(toSeek);
								rLength = binaryQueFile.read();
								for (int i = 0; i < rLength + 1; i++) {
									toSeek++;
								}
								counter++;
							}
							binaryQueFile.seek(toSeek);
							int currentLength = binaryQueFile.read();
							binaryQueFile.seek(toSeek);
							binaryQueFile.writeByte(updateColumnValue.length());
							binaryQueFile.seek(toSeek + 1);
							for (int i = 0; i < currentLength; i++) {
								binaryQueFile.writeByte(00);
							}
							binaryQueFile.seek(toSeek + 1);
							binaryQueFile.writeBytes(updateColumnValue);
							if (j == recNo.size())
								break;
						}
						startAddress += 2;
						binaryQueFile.seek(startAddress);
					}
				}
			}
			System.out.println(j + " Row(s) updated");
			binaryQueFile.close();
		} catch (Exception ex) {
			// Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void parseShowQuery(String queryString) {
		try {
			if (queryString.equalsIgnoreCase("show tables")) {
				System.out.println("-----------");
				System.out.println("Table Names");
				System.out.println("-----------");
				File file = new File(USER_DATA_PATH);
				String[] tb_names = file.list();
				for (String name : tb_names) {
					if (new File(USER_DATA_PATH + name).isDirectory()) {
						System.out.println(name);
					}
				}
				
					System.out.println(DAVISBASE_TABLES);
					System.out.println(DAVISBASE_COLUMNS);
				
				System.out.println("-----------");
			} else
				System.out.println("Incorrect syntax");
		} catch (Exception e) {
			Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public static void parseSelectQuery(String queryString) {
		try {
			ArrayList<String> queryTokenSel = new ArrayList<String>(Arrays.asList(queryString.split(" ")));
			String controllingColumnName = null;
			String compVal = null;
			String tableName = queryTokenSel.get(3);
			if (tableName.equalsIgnoreCase("davisbase_tables")) {
				parseShowQuery("show tables");
			} else {
				if (queryTokenSel.size() > 4) {
					controllingColumnName = queryTokenSel.get(5);
					compVal = queryTokenSel.get(7);
				}
				File TableMetaFolder = new File(USER_DATA_PATH + tableName);
				if (!TableMetaFolder.exists()) {
					System.out.println("Table doesn't exist..");
					return;
				}
				File[] fileNames = TableMetaFolder.listFiles();
				ArrayList<String> columnNameList = new ArrayList<String>();
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i].isFile()) {
						String columnNdxName = fileNames[i].getName();
						if (columnNdxName.contains(INDEX_EXTENSION)) {
							columnNameList.add(columnNdxName.substring(0, columnNdxName.indexOf(".")));
						}
					}
				}
				ArrayList<String> attributeOrder = new ArrayList<>();
				for (int o = 0; o < columnNameList.size() + 15; o++) {
					attributeOrder.add(".");
				}

				for (int g = 0; g < columnNameList.size(); g++) {
					RandomAccessFile fileIndex = new RandomAccessFile(
							USER_DATA_PATH + tableName + FORWARDSLASH + columnNameList.get(g) + INDEX_EXTENSION,
							MODE_RW);
					fileIndex.seek(2);
					int position = fileIndex.read();
					attributeOrder.set(position, columnNameList.get(g));
					fileIndex.close();
				}
				ArrayList<Integer> matchId = new ArrayList<>();
				ArrayList<Integer> matchIndex = new ArrayList<>();

				if (queryTokenSel.size() <= 4) {

					if (queryTokenSel.get(1).equals("*")) {
						ArrayList<Character> selectDisplayRowData = new ArrayList<>();
						RandomAccessFile binaryQueFile = new RandomAccessFile(
								USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
						int startAdd = 8;
						binaryQueFile.seek(startAdd);
						int temp = binaryQueFile.readShort();
						while (temp != 0) {
							if (-1 == temp) {
								startAdd = startAdd + 2;
								binaryQueFile.seek(startAdd);
								temp = binaryQueFile.readShort();
							} else {
								binaryQueFile.seek(startAdd);
								temp = binaryQueFile.readShort();
								binaryQueFile.seek(temp);
								binaryQueFile.seek(temp + 5);
								int noCol = binaryQueFile.read();
								int seekRLength = temp + 5 + noCol + 1;
								binaryQueFile.seek(seekRLength);
								int firstLength = binaryQueFile.read();
								for (int i = 0; i < firstLength; i++) {
									binaryQueFile.seek(seekRLength + 1);
									char charVal = (char) (binaryQueFile.read());
									selectDisplayRowData.add(charVal);
								}
								int sRLength = seekRLength + firstLength + 1;
								selectDisplayRowData.add('\t');
								selectDisplayRowData.add('\t');
								for (int k = 1; k < noCol; k++) {
									binaryQueFile.seek(sRLength);
									int rLength = binaryQueFile.read();
									for (int i = 0; i < rLength; i++) {
										sRLength += 1;
										binaryQueFile.seek(sRLength);
										char charVal = (char) (binaryQueFile.read());
										selectDisplayRowData.add(charVal);
									}
									selectDisplayRowData.add('\t');
									sRLength += 1;
									selectDisplayRowData.add('\t');
								}
								startAdd = startAdd + 2;
								selectDisplayRowData.add(';');
								binaryQueFile.seek(startAdd);
							}
						}
						int y = 0;
						for (int i = 0; i < columnNameList.size(); i++) {
							System.out.print(attributeOrder.get(i));
							System.out.print("\t\t");
							y++;
						}
						int lines = y * 16;
						System.out.println();
						System.out.println(line(HYPHEN, lines));
						String finalData = "";
						for (int j = 0; j < selectDisplayRowData.size(); j++) {
							finalData = finalData + selectDisplayRowData.get(j);
						}
						String rowdata[] = finalData.split(SEMI_COLON);
						for (int k = 0; k < rowdata.length - 1; k++) {
							System.out.println(rowdata[k]);
						}
						System.out.println(line(HYPHEN, lines));
						binaryQueFile.close();
					} else {
						RandomAccessFile binaryQueFile = new RandomAccessFile(
								USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
						ArrayList<Character> selectDispRowData = new ArrayList<>();
						int startAddress = 8;
						binaryQueFile.seek(startAddress);
						int readValue = binaryQueFile.readShort();
						while (readValue != 0) {
							if (-1 == readValue) {
								startAddress = startAddress + 2;
								binaryQueFile.seek(startAddress);
								readValue = binaryQueFile.readShort();
							} else {
								binaryQueFile.seek(startAddress);
								readValue = binaryQueFile.readShort();
								binaryQueFile.seek(readValue);
								binaryQueFile.seek(readValue + 5);
								int numOfCol = binaryQueFile.read();
								int seekRLength = readValue + 5 + numOfCol + 1;
								binaryQueFile.seek(seekRLength);
								int firstLength = binaryQueFile.read();
								for (int i = 0; i < firstLength; i++) {
									binaryQueFile.seek(seekRLength + 1);
									char charVal = (char) (binaryQueFile.read());
									selectDispRowData.add(charVal);
								}
								int StrLength = seekRLength + firstLength + 1;
								selectDispRowData.add('`');
								for (int k = 1; k < numOfCol; k++) {
									binaryQueFile.seek(StrLength);
									int rLength = binaryQueFile.read();
									for (int i = 0; i < rLength; i++) {
										StrLength += 1;
										binaryQueFile.seek(StrLength);
										char charVal = (char) (binaryQueFile.read());
										selectDispRowData.add(charVal);
									}
									StrLength += 1;
									selectDispRowData.add('`');
								}
								startAddress = startAddress + 2;
								selectDispRowData.add(';');
								binaryQueFile.seek(startAddress);
							}
						}
						String[] selectAttributeList = queryTokenSel.get(1).split(",");
						int[] selectAttributeIndex = new int[selectAttributeList.length];
						for (int i = 0; i < selectAttributeList.length; i++) {
							selectAttributeIndex[i] = attributeOrder.indexOf(selectAttributeList[i]);
						}
						String finalData = "";
						for (int j = 0; j < selectDispRowData.size(); j++) {
							finalData = finalData + selectDispRowData.get(j);
						}
						ArrayList<String> rowDataList = new ArrayList<String>(
								Arrays.asList(finalData.split(SEMI_COLON)));
						ArrayList<ArrayList<String>> selectColumn = new ArrayList<>();
						ArrayList<String> selectDataAttributeWise = new ArrayList<>();
						for (int k = 0; k < rowDataList.size() - 1; k++) {
							String columnExtract[] = rowDataList.get(k).split("`");
							for (int l = 0; l < selectAttributeList.length; l++) {
								selectDataAttributeWise.add(columnExtract[selectAttributeIndex[l]]);
							}
							selectColumn.add(selectDataAttributeWise);
						}
						for (int y = 0; y < selectAttributeList.length; y++) {
							System.out.print(selectAttributeList[y] + TAB_SEPARATOR);
						}
						System.out.println();
						System.out.println(line(HYPHEN, 16));
						for (int n = 0; n < rowDataList.size() - 1; n++) {
							String[] dispOP = rowDataList.get(n).split("`");
							for (int i = 0; i < selectAttributeList.length; i++) {
								System.out.print(dispOP[selectAttributeIndex[i]] + TAB_SEPARATOR);
							}
							System.out.println();
						}
						System.out.println(line(HYPHEN, 16));
						binaryQueFile.close();
					}
				} else if (queryTokenSel.contains("where")) {
					if (controllingColumnName.equals("rowid") || controllingColumnName.equals("row_id")) {
						int flag = 0;
						int rowNo = Integer.parseInt(queryTokenSel.get(7));
						String operator = queryTokenSel.get(6);
						if (operator.equals("=")) {
							flag = 1;
						} else if (operator.equals("<")) {
							flag = 2;
						} else if (operator.equals(">")) {
							flag = 3;
						} else {
							flag = 0;
						}
						RandomAccessFile binaryQueFile = new RandomAccessFile(
								USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
						int startAddress, result;

						switch (flag) {
						case 0:
							System.out.println(operator + " Operator not supported");
							break;
						case 1:
							binaryQueFile.seek(0x10);
							startAddress = 0x08;
							result = 0;
							binaryQueFile.seek(startAddress);
							while (binaryQueFile.readShort() != 0) {
								binaryQueFile.seek(startAddress);
								result = binaryQueFile.readShort();
								result += 4;
								binaryQueFile.seek(result);
								int recVal = binaryQueFile.read();

								if (recVal == rowNo) {
									matchIndex.add(rowNo);
									matchId.add(result);
								}
								startAddress += 2;
							}
							break;
						case 2:
							binaryQueFile.seek(0x10);
							startAddress = 0x08;
							result = 0;
							binaryQueFile.seek(startAddress);
							while (binaryQueFile.readShort() != 0) {
								binaryQueFile.seek(startAddress);
								result = binaryQueFile.readShort();
								result += 4;
								binaryQueFile.seek(result);
								int recVal = binaryQueFile.read();
								if (recVal < rowNo) {
									matchIndex.add(rowNo);
									matchId.add(result);
								}
								startAddress += 2;
							}
							break;
						case 3:
							binaryQueFile.seek(0x10);
							startAddress = 0x08;
							result = 0;
							binaryQueFile.seek(startAddress);
							while (binaryQueFile.readShort() != 0) {
								binaryQueFile.seek(startAddress);
								result = binaryQueFile.readShort();
								result += 4;
								binaryQueFile.seek(result);
								int rec_val = binaryQueFile.read();
								if (rec_val > rowNo) {
									matchIndex.add(rowNo);
									matchId.add(result);
								}
								startAddress += 2;
							}
							break;
						}

						ArrayList<Character> selectDisplayRowData = new ArrayList<>();
						RandomAccessFile binaryQueFileTbl = new RandomAccessFile(
								USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
						for (int r = 0; r < matchId.size(); r++) {
							binaryQueFile.seek(matchId.get(r));

							int readValue = matchId.get(r);
							binaryQueFileTbl.seek(readValue + 1);
							int nCol = binaryQueFileTbl.read();

							int seekRLength = readValue + 1 + nCol + 1;
							binaryQueFileTbl.seek(seekRLength);

							int firstLength = binaryQueFileTbl.read();

							for (int i = 0; i < firstLength; i++) {

								binaryQueFileTbl.seek(seekRLength + 1);
								char charVal = (char) (binaryQueFileTbl.read());
								selectDisplayRowData.add(charVal);
							}
							int sRLength = seekRLength + firstLength + 1;
							selectDisplayRowData.add('\t');
							selectDisplayRowData.add('\t');
							for (int k = 1; k < nCol; k++) {
								binaryQueFileTbl.seek(sRLength);
								int rLength = binaryQueFileTbl.readByte();
								for (int i = 0; i < rLength; i++) {
									sRLength += 1;
									binaryQueFileTbl.seek(sRLength);
									char charVal = (char) (binaryQueFileTbl.read());
									selectDisplayRowData.add(charVal);
								}
								selectDisplayRowData.add('\t');
								sRLength += 1;
								selectDisplayRowData.add('\t');
							}
							selectDisplayRowData.add(';');
						}
						int dashN = 0;
						for (int i = 0; i < columnNameList.size(); i++) {
							System.out.print(attributeOrder.get(i));
							System.out.print("\t\t");
							dashN++;
						}
						int lines = dashN * 16;
						System.out.println();
						System.out.println(line(HYPHEN, lines));

						String finalData = "";
						for (int j = 0; j < selectDisplayRowData.size(); j++) {
							finalData = finalData + selectDisplayRowData.get(j);
						}
						String rowData[] = finalData.split(SEMI_COLON);
						for (int k = 0; k < rowData.length; k++) {
							System.out.println(rowData[k]);
						}
						System.out.println(line(HYPHEN, lines));
						binaryQueFile.close();
						binaryQueFileTbl.close();
					} else if ((columnNameList.contains(controllingColumnName))) {
						ArrayList<Character> selectDisplayRowData = new ArrayList<>();
						RandomAccessFile binaryQueFile = new RandomAccessFile(
								USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
						int startAdd = 8;
						binaryQueFile.seek(startAdd);
						int temp = binaryQueFile.readShort();
						while (temp != 0) {
							if (-1 == temp) {
								startAdd = startAdd + 2;
								binaryQueFile.seek(startAdd);
								temp = binaryQueFile.readShort();
							} else {
								binaryQueFile.seek(startAdd);
								temp = binaryQueFile.readShort();
								binaryQueFile.seek(temp);
								binaryQueFile.seek(temp + 5);
								int nCol = binaryQueFile.read();
								int seekRLength = temp + 5 + nCol + 1;
								binaryQueFile.seek(seekRLength);
								int firstLength = binaryQueFile.read();
								for (int i = 0; i < firstLength; i++) {
									binaryQueFile.seek(seekRLength + 1);
									char charVal = (char) (binaryQueFile.read());
									selectDisplayRowData.add(charVal);
								}
								int sRLength = seekRLength + firstLength + 1;
								selectDisplayRowData.add('\t');
								selectDisplayRowData.add('\t');
								for (int k = 1; k < nCol; k++) {
									binaryQueFile.seek(sRLength);
									int rLength = binaryQueFile.read();
									for (int i = 0; i < rLength; i++) {
										sRLength += 1;
										binaryQueFile.seek(sRLength);
										char charVal = (char) (binaryQueFile.read());
										selectDisplayRowData.add(charVal);
									}
									selectDisplayRowData.add('\t');
									sRLength += 1;
									selectDisplayRowData.add('\t');
								}
								startAdd = startAdd + 2;
								selectDisplayRowData.add(';');
								binaryQueFile.seek(startAdd);
							}
						}
						ArrayList<Character> selectDispRowData = new ArrayList<>();
						int startAddress = 8;
						binaryQueFile.seek(startAddress);
						int readValue = binaryQueFile.readShort();
						while (readValue != 0) {
							if (-1 == temp) {
								startAdd = startAdd + 2;
								binaryQueFile.seek(startAdd);
								temp = binaryQueFile.readShort();
							} else {
								binaryQueFile.seek(startAddress);
								readValue = binaryQueFile.readShort();
								binaryQueFile.seek(readValue);
								binaryQueFile.seek(readValue + 5);
								int numOfCol = binaryQueFile.read();
								int seekRLength = readValue + 5 + numOfCol + 1;
								binaryQueFile.seek(seekRLength);
								int firstLength = binaryQueFile.read();
								for (int i = 0; i < firstLength; i++) {
									binaryQueFile.seek(seekRLength + 1);
									char charVal = (char) (binaryQueFile.read());
									selectDispRowData.add(charVal);
								}
								int strLength = seekRLength + firstLength + 1;
								selectDispRowData.add('`');
								for (int k = 1; k < numOfCol; k++) {
									binaryQueFile.seek(strLength);
									int rLength = binaryQueFile.read();
									for (int i = 0; i < rLength; i++) {
										strLength += 1;
										binaryQueFile.seek(strLength);
										char charVal = (char) (binaryQueFile.read());
										selectDispRowData.add(charVal);
									}
									strLength += 1;
									selectDispRowData.add('`');
								}
								startAddress = startAddress + 2;
								selectDispRowData.add(';');
								binaryQueFile.seek(startAddress);
							}
						}
						String finalData = "";
						for (int j = 0; j < selectDispRowData.size(); j++) {
							finalData = finalData + selectDispRowData.get(j);
						}
						ArrayList<String> rowDataList = new ArrayList<String>(
								Arrays.asList(finalData.split(SEMI_COLON)));
						int indexOfQueryElement = attributeOrder.indexOf(controllingColumnName);
						ArrayList<Integer> colNameList = new ArrayList<>();
						ArrayList<String> selectColumn = new ArrayList<>();
						for (int k = 0; k < rowDataList.size() - 1; k++) {
							String column_extract[] = rowDataList.get(k).split("`");
							String selectDataAttributeWise = column_extract[indexOfQueryElement];
							selectColumn.add(selectDataAttributeWise);
						}
						int temp2 = 0;
						int flag = 0;
						String operator = queryTokenSel.get(6);
						if (operator.equals("=")) {
							flag = 1;
						} else if (operator.equals("<")) {
							flag = 2;
						} else if (operator.equals(">")) {
							flag = 3;
						} else {
							flag = 0;
						}

						if (flag == 1) {
							for (String compareValue : selectColumn) {
								if (compareValue.equalsIgnoreCase(compVal)) {
									colNameList.add(temp2);
								}
								temp2++;
							}
						} else if (flag == 2) {
							for (String cv : selectColumn) {
								if (Integer.parseInt(cv) < Integer.parseInt(compVal)) {
									colNameList.add(temp2);
								}
								temp2++;
							}
						} else if (flag == 3) {
							for (String cv : selectColumn) {
								if (Integer.parseInt(cv) > Integer.parseInt(compVal)) {
									colNameList.add(temp2);
								}
								temp2++;
							}
						}
						if (queryTokenSel.get(1).equals("*")) {
							for (int i = 0; i < columnNameList.size(); i++) {
								System.out.print(attributeOrder.get(i));
								System.out.print("\t\t");
							}
							System.out.println();
							System.out.println(line(HYPHEN, 32));
							for (int n = 0; n < colNameList.size(); n++) {
								System.out.println(rowDataList.get(colNameList.get(n)).replaceAll("`", "\t\t"));
							}
							System.out.println(line(HYPHEN, 32));
						} else {
							String[] queryColName = queryTokenSel.get(1).split(",");
							int[] qArray = new int[queryColName.length];
							for (int i = 0; i < queryColName.length; i++)
								qArray[i] = attributeOrder.indexOf(queryColName[i]);

							ArrayList<ArrayList<String>> selectCoulmnAttributeWise = new ArrayList<>();
							ArrayList<String> selectDataAttributeWise = new ArrayList<>();
							for (int k = 0; k < rowDataList.size() - 1; k++) {
								String columnExtract[] = rowDataList.get(k).split("`");
								for (int l = 0; l < queryColName.length; l++) {
									selectDataAttributeWise.add(columnExtract[qArray[l]]);
								}
								selectCoulmnAttributeWise.add(selectDataAttributeWise);
							}

							for (int y = 0; y < queryColName.length; y++) {
								System.out.print(queryColName[y] + TAB_SEPARATOR);
							}
							System.out.println();
							System.out.println(line(HYPHEN, 16));
							for (int n = 0; n < rowDataList.size() - 1; n++) {
								String[] dispOP = rowDataList.get(colNameList.get(n)).split("`");
								for (int i = 0; i < queryColName.length; i++) {
									System.out.print(dispOP[qArray[i]] + TAB_SEPARATOR);
								}
								System.out.println();
							}
							System.out.println(line(HYPHEN, 16));
						}
						binaryQueFile.close();
					} else {
						System.out.println("Column " + controllingColumnName + " not found in " + tableName + " table");
					}
				} else {
					System.out.println("Syntax error....");
				}
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Stub method for creating new tables
	 * 
	 * @param queryString is a String of the user input
	 */
	public static void parseCreateQuery(String createQueryString) {
		ArrayList<String> columnNameList = new ArrayList<>();
		ArrayList<String> createQueryToken = new ArrayList<String>(Arrays.asList(createQueryString.split(" ")));
		int freq;
		freq = Collections.frequency(createQueryToken, "primary");
		String tableName = createQueryToken.get(2);
		String temp[] = tableName.replace("(", " ").split(" ");
		DavisBase db = new DavisBase();
		try {
			File file = new File(USER_DATA_PATH + temp[0]);
			boolean fileExists = file.exists();
			String dataTypeString;

			String queryColName;
			createQueryString = createQueryString.substring(createQueryString.indexOf("(") + 1,
					createQueryString.length() - 1);
			int recordSize = 0;
			int serialCode = 0;
			String dataType[] = createQueryString.split(",");
			int columnOrder = 0;
			if (!fileExists) {
				if (freq <= 1) {
					Boolean dataTypeFlag = false;
					for (int i = 0; i < dataType.length; i++) {
						dataTypeString = dataType[i];
						String dType[] = dataTypeString.split(" ");
						queryColName = dataType[i].substring(0, dataType[i].indexOf(" "));
						columnNameList.add(queryColName);
						dataTypeFlag = db.validateDataType(dType[1]);
						if (dataTypeFlag == false) {
							deleteDir(file);
							return;
						}
						String flagP = null;

						if (dType[1].equalsIgnoreCase("int")) {
							recordSize = recordSize + 4;
							serialCode = 0x06;
						} else if (dType[1].equalsIgnoreCase("tinyint")) {
							recordSize = recordSize + 1;
							serialCode = 0x04;
						} else if (dType[1].equalsIgnoreCase("smallint")) {
							recordSize = recordSize + 2;
							serialCode = 0x05;
						} else if (dType[1].equalsIgnoreCase("bigint")) {
							recordSize = recordSize + 8;
							serialCode = 0x07;
						} else if (dType[1].equalsIgnoreCase("real")) {
							recordSize = recordSize + 4;
							serialCode = 0x08;
						} else if (dType[1].equalsIgnoreCase("double")) {
							recordSize = recordSize + 8;
							serialCode = 0x09;
						} else if (dType[1].equalsIgnoreCase("datetime")) {
							recordSize = recordSize + 8;
							serialCode = 0x0A;
						} else if (dType[1].equalsIgnoreCase("date")) {
							recordSize = recordSize + 8;
							serialCode = 0x0B;
						} else if (dType[1].equalsIgnoreCase("text")) {
							serialCode = 0x0C;
						}
						int clmOrder = 0;
						int dataTypeLen = dType[1].length();
						int columnLength = queryColName.length();
						int tableLength = temp[0].length();
						int isNullLength = 0;
						if (dataTypeString.toLowerCase().contains("primary key")
								|| dataTypeString.toLowerCase().contains("[not null]")) {
							if (dataTypeString.toLowerCase().contains("primary key")) {
								isNullLength = 3;
								flagP = "PRI";
							} else {
								isNullLength = 2;
								flagP = NO;
							}
						} else {
							isNullLength = 3;
							flagP = "YES";
						}
						int totalSize = 1 + tableLength + columnLength + dataTypeLen + 1 + isNullLength + 6;
						if (dataTypeFlag && freq <= 1) {
							file.mkdir();
							RandomAccessFile tableColumn = new RandomAccessFile(
									USER_DATA_PATH + temp[0] + FORWARDSLASH + queryColName + INDEX_EXTENSION, MODE_RW);
							tableColumn.setLength(pageSize);
							tableColumn.seek(0);
							tableColumn.writeByte(serialCode);
							int nullVal;
							if (flagP.equalsIgnoreCase("yes")) {
								nullVal = 1;
							} else {
								nullVal = 0;
							}
							tableColumn.writeByte(nullVal);
							tableColumn.write(columnOrder);
							columnOrder++;
							tableColumn.writeByte(0x00);
							tableColumn.writeByte(0x10);
							RandomAccessFile tableColumnFile = new RandomAccessFile(
									CATALOG_PATH + DAVISBASE_COLUMNS + TABLE_EXTENSION, MODE_RW);
							tableColumnFile.setLength(2048);
							tableColumnFile.seek(1);
							int columnNumber = tableColumnFile.read();
							tableColumnFile.seek(2);
							int charVal = tableColumnFile.readShort();
							tableColumnFile.seek(2);
							tableColumnFile.writeShort((int) (charVal - totalSize));
							tableColumnFile.seek(charVal - totalSize);
							columnNumber++;
							tableColumnFile.writeByte(1);
							tableColumnFile.writeByte(columnNumber);
							tableColumnFile.writeByte(tableLength);
							tableColumnFile.writeBytes(temp[0]);
							tableColumnFile.writeByte(columnLength);
							tableColumnFile.writeBytes(queryColName);
							tableColumnFile.writeByte(dataTypeLen);
							tableColumnFile.writeBytes(dType[1]);
							tableColumnFile.writeByte(1);
							clmOrder++;
							tableColumnFile.writeByte(clmOrder);
							tableColumnFile.writeByte(isNullLength);
							tableColumnFile.writeBytes(flagP);
							tableColumnFile.seek(1);
							tableColumnFile.writeByte(columnNumber);
							tableColumnFile.seek(2);
							tableColumnFile.writeShort((int) (charVal - totalSize));
							tableColumnFile.seek(8 + columnNumber * 2 - 2);
							tableColumnFile.writeShort(charVal - totalSize);
							tableColumn.close();
							tableColumnFile.close();
						}
					}
					if (dataTypeFlag && freq <= 1) {
						file.mkdir();
						RandomAccessFile tableHoldingFile = new RandomAccessFile(
								CATALOG_PATH + DAVISBASE_TABLES + TABLE_EXTENSION, MODE_RW);
						tableHoldingFile.seek(1);
						int recordCount = tableHoldingFile.read();
						tableHoldingFile.seek(2);
						pageSz = tableHoldingFile.readShort();
						tableHoldingFile.setLength(pageSize);
						pageSz = pageSz - temp[0].length() - 1;
						tableHoldingFile.seek(pageSz);
						recordCount = recordCount + 1;
						tableHoldingFile.writeByte(recordCount);
						tableHoldingFile.writeBytes(temp[0]);
						tableHoldingFile.seek(1);
						tableHoldingFile.writeByte(recordCount);
						tableHoldingFile.seek(2);
						tableHoldingFile.writeShort((int) (pageSz));
						tableHoldingFile.seek(8 + recordCount * 2 - 2);
						tableHoldingFile.writeShort((int) pageSz);
						RandomAccessFile userTableFile = new RandomAccessFile(
								USER_DATA_PATH + temp[0] + FORWARDSLASH + temp[0] + TABLE_EXTENSION, MODE_RW);
						userTableFile.setLength(pageSize);
						userTableFile.seek(0);
						userTableFile.write(0x0d);
						userTableFile.seek(1);
						int colRecCount = userTableFile.readByte();
						int colHeaderSize = 0;
						if (colRecCount == 0) {
							pageSz_col = 512;
							for (int cl = 0; cl < columnNameList.size(); cl++) {
								colHeaderSize += columnNameList.get(cl).length();
							}
							userTableFile.seek(pageSz_col - colHeaderSize - columnNameList.size());

							for (int i = 0; i < columnNameList.size(); i++) {
								userTableFile.writeByte(columnNameList.get(i).length());
								userTableFile.writeBytes(columnNameList.get(i));
							}
							pageSz_col = pageSz_col - colHeaderSize - columnNameList.size();
							userTableFile.seek(2);
							userTableFile.writeShort((int) pageSz_col);
						}
						tableHoldingFile.close();
						userTableFile.close();
					}
				} else {
					System.out.println("A table cannot have two primary keys.....");
				}
				System.out.println("Table created..");
			} else {
				System.out.println("Table " + temp[0] + " already exists");
			}
		} catch (Exception e) {
			System.out.println("Check your command. " + e.getMessage());
		}
	}

	public boolean validateDataType(String type) {
		try {
			switch (type) {
			case "int":
			case "smallint":
			case "tinyint":
			case "bigint":
			case "null":
			case "text":
			case "real":
			case "double":
			case "datetime":
			case "date":
				return true;
			default:
				throw new Exception("invalid Datatype!!!");

			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public static void parseInsertQuery(String insertTableQueryString) {

		try {
			ArrayList<String> insertQueryToken = new ArrayList<String>(
					Arrays.asList(insertTableQueryString.split(" ")));
			String tableName = insertQueryToken.get(4);
			int try1 = 0;
			int isPk = 0;

			File tableMetaFolder = new File(USER_DATA_PATH + tableName);
			if (tableMetaFolder.exists()) {
				String insertColumnList = insertQueryToken.get(3);
				String insertColumnValues = insertQueryToken.get(6);
				insertColumnList = insertColumnList.substring(insertColumnList.indexOf("(") + 1,
						insertColumnList.length() - 1);
				ArrayList<String> insertColumnArrayList = new ArrayList<>();
				String insertColumnStringArray[] = insertColumnList.split(",");
				for (int i = 0; i < insertColumnStringArray.length; i++) {
					insertColumnArrayList.add(insertColumnStringArray[i]);
				}

				insertColumnValues = insertColumnValues.substring(insertColumnValues.indexOf("(") + 1,
						insertColumnValues.length() - 1);
				String insertColumnValueArray[] = insertColumnValues.split(",");

				ArrayList<String> insertColumnValueList = new ArrayList<>();

				for (int y = 0; y < insertColumnValueArray.length; y++) {
					insertColumnValueList.add(insertColumnValueArray[y]);
				}
				ArrayList<String> insertLeftColumn = new ArrayList<>();
				Boolean length = true;
				int diff = insertColumnStringArray.length - insertColumnValueArray.length;
				if (insertColumnStringArray.length < insertColumnValueArray.length) {
					length = false;
				} else {
					for (int l = insertColumnStringArray.length; l > (insertColumnStringArray.length - diff); l--) {
						String remaining = insertColumnStringArray[l - 1];
						insertLeftColumn.add(remaining);
					}
				}

				ArrayList<String> columnName = new ArrayList<String>();
				File[] fileNames = tableMetaFolder.listFiles();
				int flag = 0;
				int nullFlag = 0;

				ArrayList<Integer> temp3 = new ArrayList<>();
				ArrayList<Integer> temp4 = new ArrayList<>();
				int cRecordSize = 0;
				ArrayList<String> tmp = new ArrayList<>();
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i].isFile()) {
						String columnNdxName = fileNames[i].getName();
						if (columnNdxName.contains(INDEX_EXTENSION)) {
							columnName.add(columnNdxName.substring(0, columnNdxName.indexOf(".")));
						}
					}
				}
				tmp = columnName;
				for (int m = 0; m < insertColumnArrayList.size(); m++) {
					if (columnName.contains(insertColumnArrayList.get(m))) {
						flag = 1;
					} else {
						flag = 0;
						break;
					}
				}

				for (int j = 0; j < insertColumnArrayList.size(); j++) {
					if (tmp.contains(insertColumnArrayList.get(j))) {
						tmp.remove(insertColumnArrayList.get(j));
					}
				}
				for (int r = 0; r < tmp.size(); r++) {
					insertLeftColumn.add(tmp.get(r));
				}
				for (int insCol = 0; insCol < insertLeftColumn.size(); insCol++) {
					try {
						RandomAccessFile cols = new RandomAccessFile(USER_DATA_PATH + tableName + FORWARDSLASH
								+ insertLeftColumn.get(insCol) + INDEX_EXTENSION, MODE_RW);
						cols.seek(0);
						try1 = cols.read();
						temp3.add(try1);
						cols.seek(1);
						isPk = cols.read();
						temp4.add(isPk);
						cols.close();
					} catch (Exception ex) {
						Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
				if (temp4.contains(0)) {
					nullFlag = 0;
				} else {
					nullFlag = 1;
				}
				ArrayList<Integer> nullType = new ArrayList<>();
				if (flag == 1 && length) {
					if (nullFlag == 1) {
						for (int y = 0; y < temp3.size(); y++) {
							if (temp3.get(y) == 0x04) {
								nullType.add(1);
							} else if (temp3.get(y) == 0x05) {
								nullType.add(2);
							} else if (temp3.get(y) == 0x06 || temp3.get(y) == 0x08) {
								nullType.add(4);
							} else if (temp3.get(y) == 0x09 || temp3.get(y) == 0x0A || temp3.get(y) == 0x0B) {
								nullType.add(8);
							} else {
								nullType.add(0);
							}
						}

						for (int insCol = 0; insCol < insertColumnStringArray.length; insCol++) {
							try {
								RandomAccessFile colsGiven = new RandomAccessFile(USER_DATA_PATH + tableName
										+ FORWARDSLASH + insertColumnStringArray[insCol] + INDEX_EXTENSION, MODE_RW);
								colsGiven.seek(0);
								int data_type = colsGiven.read();
								if (data_type == 0x04) {
									cRecordSize += 1;
								} else if (data_type == 0x05) {
									cRecordSize += 2;
								} else if (data_type == 0x06) {
									cRecordSize += 4;
								} else if (data_type == 0x07) {
									cRecordSize += 8;
								} else if (data_type == 0x08) {
									cRecordSize += 4;
								} else if (data_type == 0x09) {
									cRecordSize += 8;
								} else if (data_type == 0x0A) {
									cRecordSize += 8;
								} else if (data_type == 0x0B) {
									cRecordSize += 8;
								} else if (data_type == 0x0C) {

									cRecordSize += insertColumnValueArray[insCol].length();
								}
								colsGiven.close();
							} catch (Exception ex) {
								Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
							}

						}
						columnName.clear();
						for (int i = 0; i < fileNames.length; i++) {
							if (fileNames[i].isFile()) {
								String columnNdxName = fileNames[i].getName();
								if (columnNdxName.contains(INDEX_EXTENSION)) {
									columnName.add(columnNdxName.substring(0, columnNdxName.indexOf(".")));
								}

							}
						}
						ArrayList<String> orderValues = new ArrayList<>();
						int nullByte = 0;
						for (int o = 0; o < columnName.size() + 15; o++) {
							orderValues.add(".");
						}
						ArrayList<String> ordValuesType = new ArrayList<>();

						// adding dummy values
						for (int i = 0; i < columnName.size() + 15; i++) {
							ordValuesType.add(".");
						}

						if (diff >= 0) {
							for (int u = 0; u < insertLeftColumn.size(); u++) {
								try {
									RandomAccessFile rColsType = new RandomAccessFile(USER_DATA_PATH + tableName
											+ FORWARDSLASH + insertLeftColumn.get(u) + INDEX_EXTENSION, MODE_RW);
									rColsType.seek(2);
									int asd = rColsType.read();
									rColsType.seek(0);
									int dtype = rColsType.read();
									if (dtype == 0x04) {
										orderValues.set(asd, "~");
										nullByte += 1;
									} else if (dtype == 0x05) {
										nullByte += 2;
										orderValues.set(asd, "~");
									} else if (dtype == 0x06 || dtype == 0x08) {
										nullByte += 4;
										orderValues.set(asd, "~");
									} else if (dtype == 0x09 || dtype == 0x0A || dtype == 0x0B) {
										nullByte += 8;
										orderValues.set(asd, "~");
									} else {
										nullByte += 0;
										orderValues.set(asd, "~");
									}
									rColsType.close();
								} catch (Exception ex) {
									Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
								}
							}
						}
						int bytes_req = 2 + 4 + (2 * columnName.size()) + 1 + cRecordSize + nullByte;
						for (int a = 0; a < insertColumnValueList.size(); a++) {
							try {
								RandomAccessFile colsOrd = new RandomAccessFile(USER_DATA_PATH + tableName
										+ FORWARDSLASH + insertColumnArrayList.get(a) + INDEX_EXTENSION, MODE_RW);
								colsOrd.seek(2);
								int ord = colsOrd.read();
								orderValues.set(ord, insertColumnValueArray[a]);
								colsOrd.close();
							} catch (Exception ex) {
								Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
							}
						}
						try {
							RandomAccessFile insertTable = new RandomAccessFile(
									USER_DATA_PATH + tableName + FORWARDSLASH + tableName + TABLE_EXTENSION, MODE_RW);
							insertTable.setLength(pageSize);
							insertTable.seek(1);
							int row_id = insertTable.read();
							row_id++;
							insertTable.seek(1);
							insertTable.write(row_id);
							insertTable.seek(2);
							pageSzI = insertTable.readShort();
							pageSzI = pageSzI - bytes_req;
							insertTable.seek(2);
							insertTable.writeShort((int) pageSzI);
							insertTable.seek(pageSzI);
							insertTable.writeByte(cRecordSize);
							insertTable.writeInt(row_id);
							int countWidth = 0;
							for (int y = 0; y < columnName.size(); y++) {
								countWidth++;
							}
							insertTable.write(countWidth);
							for (int i = 0; i < columnName.size(); i++) {
								RandomAccessFile deleteColList = new RandomAccessFile(
										USER_DATA_PATH + tableName + FORWARDSLASH + columnName.get(i) + INDEX_EXTENSION,
										MODE_RW);
								deleteColList.seek(0);
								int dt = deleteColList.read();

								deleteColList.seek(2);
								int op = deleteColList.read();
								ordValuesType.set(op, Integer.toString(dt));
								deleteColList.close();
							}
							for (int i = 0; i < ordValuesType.size(); i++) {
								if (ordValuesType.get(i).equalsIgnoreCase(".")) {

								} else {
									insertTable.writeByte(Integer.parseInt(ordValuesType.get(i)));
								}
							}
							for (int i = 0; i < orderValues.size(); i++) {
								if (!orderValues.get(i).equalsIgnoreCase(".")) {
									if (i == 0) {
										insertTable.writeByte(String.valueOf(row_id).length());
										insertTable.writeBytes(String.valueOf(row_id));
									} else {
										insertTable.writeByte(orderValues.get(i).length());
										insertTable.writeBytes(orderValues.get(i));
									}
								}
							}
							insertTable.seek(8 + 2 * row_id - 2);
							insertTable.writeShort((int) pageSzI);

							for (int i = 0; i < columnName.size(); i++) {
								try {
									RandomAccessFile recordColumn = new RandomAccessFile(USER_DATA_PATH + tableName
											+ FORWARDSLASH + columnName.get(i) + INDEX_EXTENSION, MODE_RW);
									recordColumn.seek(3);
									int positionX = recordColumn.readShort();
									recordColumn.seek(2);
									int ordP = recordColumn.read();
									recordColumn.seek(positionX);
									recordColumn.writeByte(row_id);
									recordColumn.writeBytes(orderValues.get(ordP));
									positionX += 16;
									recordColumn.seek(3);
									recordColumn.writeShort(positionX);
									recordColumn.close();
								} catch (Exception ex) {
									Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
								}
							}
							System.out.println("Row Inserted");
							insertTable.close();
						} catch (Exception ex) {
							Logger.getLogger(DavisBase.class.getName()).log(Level.SEVERE, null, ex);
						}
					} else {
						System.out.println("Null Values not allowed");
					}
				} else {
					System.out.println("Column does not exist.....");
				}
			} else {
				System.out.println("Please create the table " + tableName + " before inserting values");
			}

		} catch (Exception e) {
			System.out.println("Please re-check your insert syntax");
		}
	}
}