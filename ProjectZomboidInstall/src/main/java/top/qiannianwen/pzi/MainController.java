package top.qiannianwen.pzi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainController {
    @FXML
    private Label text;
    @FXML
    private Button button;

    private Integer flag;

    @FXML
    protected void onButtonClick() {
        if (flag == 0) {
            System.exit(0);
        }
        if (flag == 1) {
            try {
                final File file32 = new File("ProjectZomboid32.json");
                final File file64 = new File("ProjectZomboid64.json");
                installFile(file32);
                installFile(file64);
                Files.copy(getClass().getResource("/ProjectZomboidFramework.jar").openStream(), Path.of("ProjectZomboidFramework.jar"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("安装成功");
                alert.setHeaderText("安装成功");
                alert.setContentText("ProjectZomboidFramework已安装。");
                alert.showAndWait();
                System.exit(0);
            } catch (IOException e) {
                text.setText("安装程序出现错误。");
                text.setTextFill(Color.RED);
                button.setText("退出");
                flag = 0;
            }
        }
    }

    public void initialize() {
        final File file32 = new File("ProjectZomboid32.json");
        final File file64 = new File("ProjectZomboid64.json");
        if (file32.exists() && file64.exists()) {
            try {
                final File jar = new File("ProjectZomboidFramework.jar");
                if (checkFile(file32) && checkFile(file64) && jar.exists()) {
                    text.setText("ProjectZomboidFramework已安装。");
                    text.setTextFill(Color.GREEN);
                    button.setText("重新安装");
                    flag = 1;
                } else {
                    text.setText("ProjectZomboidFramework未安装。");
                    text.setTextFill(Color.YELLOW);
                    button.setText("安装");
                    flag = 1;
                }
            } catch (IOException e) {
                text.setText("安装程序出现错误。");
                text.setTextFill(Color.RED);
                button.setText("退出");
                flag = 0;
            }
        } else {
            text.setText("未找到ProjectZomboid32.json或ProjectZomboid64.json文件。");
            text.setTextFill(Color.RED);
            button.setText("退出");
            flag = 0;
        }
    }

    private boolean checkFile(File file) throws IOException {
        String string = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        final JSONObject jsonObject = JSON.parseObject(string);
        final String mainClass = jsonObject.getString("mainClass");
        final List<String> classpathList = jsonObject.getJSONArray("classpath").toList(String.class);
        return "top/qiannianwen/pzf/MainScreenState".equals(mainClass) && classpathList.contains("ProjectZomboidFramework.jar");
    }

    private void installFile(File file) throws IOException {
        String string = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        final JSONObject jsonObject = JSON.parseObject(string);
        final List<String> classpathList = jsonObject.getJSONArray("classpath").toList(String.class);
        classpathList.remove("ProjectZomboidFramework.jar");
        classpathList.add("ProjectZomboidFramework.jar");

        jsonObject.put("mainClass", "top/qiannianwen/pzf/MainScreenState");
        jsonObject.put("classpath", classpathList);
        Files.writeString(file.toPath(), jsonObject.toJSONString(JSONWriter.Feature.PrettyFormat), StandardCharsets.UTF_8);
    }

}