package com.dyoung.swingy.util;

import com.dyoung.swingy.model.Artifact;
import com.dyoung.swingy.model.Candidate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static final String DATA_BASE_URL = "jdbc:h2:./src/main/resources/h2";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    public static void connect() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DATA_BASE_URL + ";IFEXISTS=TRUE", USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            reset();
        }
    }

    private static void reset() {
        try {
            FileReader fr = new FileReader("./src/main/resources/init.sql");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();

            String[] commands = sb.toString().split(":");

            connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            for (String command : commands) {
                if (command.trim().length() > 0) {
                    statement.execute(command);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (connection != null)
                connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() {
        if (connection == null)
            connect();
        return connection;
    }

    public static int insertCandidate(String heroName, String className, int level, int experience, int attack, int defense, int hitPoints) {
        String sql = "INSERT INTO candidates (heroName, heroClass, level, experience, attack, defense, hitPoints) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int id = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, heroName);
            ps.setString(2, className);
            ps.setInt(3, level);
            ps.setInt(4, experience);
            ps.setInt(5, attack);
            ps.setInt(6, defense);
            ps.setInt(7, hitPoints);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next())
                id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static ArrayList<String> selectNames() {
        String sql = "SELECT * FROM candidates";
        ArrayList<String> arrayList = new ArrayList<>();

        try (Statement statement = getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                arrayList.add(String.format("%s", rs.getString("heroName")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrayList;
    }

    public static Candidate selectCandidateByName(String name) {
        String sql = "SELECT * FROM candidates WHERE heroName = ?";
        Candidate candidate = null;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CandidateBuilder builder = new CandidateBuilder();
                builder.setId(rs.getInt("id"));
                builder.setHeroName(rs.getString("heroName"));
                builder.setHeroClass(rs.getString("heroClass"));
                builder.setLevel(rs.getInt("level"));
                builder.setExperience(rs.getInt("experience"));
                builder.setAttack(rs.getInt("attack"));
                builder.setDefense(rs.getInt("defense"));
                builder.setHitPoints(rs.getInt("hitPoints"));

                if (rs.getInt("weapon") != 0)
                    builder.setWeapon(new Artifact("Technology", rs.getInt("weapon")));
                if (rs.getInt("armor") != 0)
                    builder.setArmor(new Artifact("Course", rs.getInt("armor")));
                if (rs.getInt("helm") != 0)
                    builder.setHelm(new Artifact("Book", rs.getInt("helm")));

                candidate = builder.getResult();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return candidate;
    }

    public static void updateCandidate(Candidate candidate) {
        String sql = "UPDATE candidates SET level = ?, experience = ?, attack = ?, defense = ?, hitPoints = ?, " +
                "weapon = ?, armor = ?, helm = ? WHERE id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, candidate.getLevel());
            ps.setInt(2, candidate.getExperience());
            ps.setInt(3, candidate.getAttack());
            ps.setInt(4, candidate.getDefense());
            ps.setInt(5, candidate.getHitPoints());

            if (candidate.getWeapon() != null) {
                ps.setInt(6, candidate.getWeapon().getPoints());
            } else {
                ps.setInt(6, 0);
            }
            if (candidate.getArmor() != null) {
                ps.setInt(7, candidate.getArmor().getPoints());
            } else {
                ps.setInt(7, 0);
            }
            if (candidate.getHelm() != null) {
                ps.setInt(8, candidate.getHelm().getPoints());
            } else {
                ps.setInt(8, 0);
            }
            ps.setInt(9, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCandidate(Candidate candidate) {
        String sql = "DELETE FROM candidates WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
