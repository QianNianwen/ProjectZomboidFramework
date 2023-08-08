module top.qiannianwen.pzi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.alibaba.fastjson2;


    opens top.qiannianwen.pzi to javafx.fxml;
    exports top.qiannianwen.pzi;
}