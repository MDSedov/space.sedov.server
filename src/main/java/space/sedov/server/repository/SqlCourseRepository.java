package space.sedov.server.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SqlCourseRepository {

    private final String url = "jdbc:postgresql://localhost:5433/demo";
    private final String username = "postgres";
    private final String password = "MD100cs16_";

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResponseService executeQuery(String query) {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            return new ResponseService(HttpStatus.OK, MessageService.OK, resultSetToJSON(resultSet));
        } catch (SQLException e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, e.getMessage());
        }
    }

    public JSONArray resultSetToJSON(ResultSet resultSet) {
        try {
            ResultSetMetaData md = resultSet.getMetaData();
            int numCols = md.getColumnCount();
            List<String> colNames = IntStream.range(0, numCols)
                    .mapToObj(i -> {
                        try {
                            return md.getColumnName(i + 1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());

            JSONArray result = new JSONArray();
            while (resultSet.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, resultSet.getObject(cn));
                    } catch (JSONException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                result.put(row);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
