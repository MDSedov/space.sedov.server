package space.sedov.server.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SqlCourseRepository implements InitializingBean {

    @Value("${spring.sql.course.datasource.url}")
    private String url;

    @Value("${spring.sql.course.datasource.username}")
    private String username;

    @Value("${spring.sql.course.datasource.password}")
    private String password;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
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

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
