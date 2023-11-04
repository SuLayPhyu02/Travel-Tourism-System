package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tt.Staff;
import tt.Destination;
import tt.Transportation;
import tt.TransportationType;
import tt.Hotel;
import tt.HotelStars;
import tt.Package;
import tt.TransportationContact;

public class DBhandler {
	private static Connection con;
	private static String userName = "root";
	private static String password = "Sulay6619#";
	private static int port = 3306;
	private static String dbName = "travel_tourism";
	private static String host = "localhost";

	public static boolean openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
			con = DriverManager.getConnection(url, userName, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean closeConnection() {
		try {
			con.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int staff_login(String email, String password, String position) {
		int id = -1;
		try {
			openConnection();
			password = Checker.digestMsg(password);
			String sql = "select sid from staffs where semail=? and spassword=? and sposition=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {

		}
		closeConnection();
		return id;
	}

	public static boolean addStaff(String name, String email, String pass, String pos, String enD, String image) {
		pass = Checker.digestMsg(pass);
		try {
			openConnection();
			String sql = "insert into staffs(sname,semail,spassword,sposition,enrollDate,staffImage) values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, pass);
			ps.setString(4, pos);
			ps.setDate(5, java.sql.Date.valueOf(enD));
			ps.setString(6, image);
			int line = ps.executeUpdate();
			closeConnection();
			return line > 0;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean addPackage(String packageName, String departureDate, String arrivalDate, int packPrice,
			String pImage, int destID) {
		try {

			openConnection();
			// Prepare the SQL query with placeholders
			String sql = "INSERT INTO packages (packageName, departureDate, arrivalDate, packPrice, pImage, destID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, packageName);
			preparedStatement.setDate(2, java.sql.Date.valueOf(departureDate));
			preparedStatement.setDate(3, java.sql.Date.valueOf(arrivalDate));
			preparedStatement.setInt(4, packPrice);
			preparedStatement.setString(5, pImage);
			preparedStatement.setInt(6, destID);

			// Execute the query
			preparedStatement.executeUpdate();

			closeConnection();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean addTransportation(String tName, String temail, String taddress, String ttName, String tphNo,
	        Destination destination, int price, String imagePath) {
	    try {
	        openConnection();

	        // Insert transportation data into the 'transportations' table
	        String insertTransportationSql = "INSERT INTO transportations (tName, temail, taddress, typeID, tImage) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = con.prepareStatement(insertTransportationSql, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, tName);
	        preparedStatement.setString(2, temail);
	        preparedStatement.setString(3, taddress);
	        preparedStatement.setString(4, ttName);
	        preparedStatement.setString(5, imagePath);

	        int rowsAffected = preparedStatement.executeUpdate();

	        // Retrieve the auto-generated transportation ID
	        int transportationID = -1;
	        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            transportationID = generatedKeys.getInt(1);
	        }

	        if (transportationID != -1) {
	            // Insert additional data into other related tables
	            String insertTransportationContactSql = "INSERT INTO transportationcontact (tphNo, transportationID) VALUES (?, ?)";
	            PreparedStatement contactStatement = con.prepareStatement(insertTransportationContactSql);
	            contactStatement.setString(1, tphNo);
	            contactStatement.setInt(2, transportationID);
	            contactStatement.executeUpdate();

	            String insertDestinationTransportationSql = "INSERT INTO destination_transportation_joint (destinationID, transportationID, price) VALUES (?, ?, ?)";
	            PreparedStatement jointStatement = con.prepareStatement(insertDestinationTransportationSql);
	            jointStatement.setInt(1, destination.getdID());
	            jointStatement.setInt(2, transportationID);
	            jointStatement.setInt(3, price);
	            jointStatement.executeUpdate();
	        }

	        closeConnection();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	public static String getProfileImage(String email) {
		String image = null;
		try {
			openConnection();
			String sql = "select staffImage from staffs where semail=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getString("staffImage");
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static String getPackageImage(int pid) {
		String image = null;
		try {
			openConnection();
			String sql = "select pImage from packages where packageID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getString("pImage");
			}
			rs.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static ArrayList<Staff> getAllStaffs() {
		ArrayList<Staff> staffs = new ArrayList<>();
		try {
			openConnection();
			String sql = "select * from staffs";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Staff s = new Staff(rs.getInt("sid"), rs.getString("sname"), rs.getString("semail"),
						rs.getString("sposition"), rs.getDate("enrollDate").toString());
				staffs.add(s);
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staffs;
	}

	public static ArrayList<Destination> getAllDestinations() {
		ArrayList<Destination> destinations = new ArrayList<>();
		try {
			openConnection();
			String sql = "select * from destinations";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Destination d = new Destination(rs.getInt("dID"), rs.getString("detName"));
				destinations.add(d);
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinations;
	}

	public static ArrayList<Transportation> getAllTransportation() {
	    ArrayList<Transportation> tran = new ArrayList<>();
	    try {
	        openConnection();
	        String sql = "SELECT tid, tname, temail, taddress, ttName, tphNo " +
	                     "FROM transportations, transportationtype, transportationcontact " +
	                     "WHERE transportations.typeID = transportationtype.ttID " +
	                     "AND transportations.tID = transportationcontact.transportationID";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Transportation t = new Transportation(
	                rs.getInt("tid"),
	                rs.getString("tname"),
	                rs.getString("temail"),
	                rs.getString("taddress"),
	                rs.getString("ttName"),
	                rs.getString("tphNo")
	            );
	            tran.add(t);
	        }
	        closeConnection();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tran;
	}

	public static ArrayList<Hotel> getAllHotels() {
		ArrayList<Hotel> hh = new ArrayList<>();
		try {
			openConnection();
			String sql = "SELECT hotels.hotelID,hotels.hotelName,hotels.hemail,hotels.haddress,hotelstandards.star, destinations.detName\r\n"
					+ "FROM hotelstandards,destinations,hotels where hotels.hsID=hotelstandards.hsID AND hotels.destinationID=destinations.dID;\r\n"
					+ "";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Hotel h = new Hotel(rs.getInt("hotelID"), rs.getString("hotelName"), rs.getString("hemail"),
						rs.getString("haddress"), rs.getInt("star"), rs.getString("detName"));
				hh.add(h);
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hh;
	}

	public static ArrayList<HotelStars> getStars() {
		ArrayList<HotelStars> hs = new ArrayList<>();
		try {
			openConnection();
			String sql = "SELECT star FROM hotelstandards join hotels ON hotels.hsID=hotelstandards.hsID";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				HotelStars s = new HotelStars(rs.getInt("star"));
				hs.add(s);
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hs;
	}

	public static ArrayList<TransportationType> getTType() {
		ArrayList<TransportationType> ta = new ArrayList<>();
		try {
			openConnection();
			String sql = "select ttName from transportationtype";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TransportationType t = new TransportationType(rs.getString("ttName"));
				ta.add(t);
			}
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ta;
	}

	public static Package getPackage(String img) {
		Package p = null;
		try {
			openConnection();
			String sql = "select * from packages where pImage=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, img);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p = new Package(rs.getInt("packageID"), rs.getString("packageName"), rs.getString("departureDate"),
						rs.getString("arrivalDate"), rs.getInt("packPrice"), rs.getString("pImage"),
						rs.getInt("destID"));
			}
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	public static ArrayList<Package> getAllPackage() {
		ArrayList<Package> pack = new ArrayList<>();
		try {
			openConnection();
			String sql = "SELECT p.packageID, p.packageName, p.departureDate, p.arrivalDate, p.packPrice, p.pImage, p.destID, d.detName "
					+ "FROM packages p, destinations d " + "WHERE p.destID = d.dID"; // Use the correct alias
																						// 'd.detName'
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Package p = new Package(rs.getInt("packageID"), rs.getString("packageName"),
						rs.getString("departureDate"), rs.getString("arrivalDate"), rs.getInt("packPrice"),
						rs.getString("pImage"), rs.getInt("destID"), rs.getString("detName")); // Use 'detName'
				pack.add(p);
			}
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pack;
	}

	public static boolean updateStaff(int id, String name, String email, String position) {

		try {
			openConnection();
			String sql = "upate staffs set sname=?,semail=?,sposition=? where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, position);
			int line = ps.executeUpdate();
			closeConnection();
			return line > 0;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean deleteStaff(int sid) {
		try {
			openConnection();
			String sql = "delete from staffs where sid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, sid);
			int line = ps.executeUpdate();
			closeConnection();
			return line > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String args[]) {
		// System.out.println(getAllDestinations());
		 System.out.println(getAllTransportation());
		// System.out.println(getAllHotels());
		// System.out.println(getStars());
		// System.out.println(getTType());
//		System.out.println(getTransportationName());
		// System.out.println(getAllStaffs());
		// System.out.println(getImage("Shwe Thwe"));
		// System.out.println(addStaff("Su Lay Phyu",
		// "salapha202@gmail.com","Sulay6619#","Admin","2023-05-28"));
//		System.out.println(getAllPackage());
	}
}
