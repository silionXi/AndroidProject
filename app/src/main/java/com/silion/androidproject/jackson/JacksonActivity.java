package com.silion.androidproject.jackson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silion.androidproject.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JacksonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackson);
    }

    public void java2Json(View view) {
        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");

        User user1 = new User();
        user1.setName("silion");
        user1.setEmail("silion@qq.com");
        user1.setAge(2);

        try {
            user1.setBirthday(dateformat.parse("10/01/2014"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();

        //User类转JSON
        String json = null;
        try {
            json = mapper.writeValueAsString(user1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // {"birthday":"2014年09月30日","name":"silion","mail":"silion@qq.com"}
        System.out.println(json);

        //Java集合转JSON
        User user2 = new User();
        user2.setName("silion");
        user2.setEmail("silion@qq.com");
        user2.setAge(2);

        try {
            user2.setBirthday(dateformat.parse("01/01/2015"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        String jsonlist = null;
        try {
            jsonlist = mapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // [{"birthday":"2014年09月30日","name":"silion","mail":"silion@qq.com"},{"birthday":"2014年12月31日","name":"silion","mail":"silion@qq.com"}]
        System.out.println(jsonlist);
    }

    public void json2Java(View view) {
        String json = "{\"age\":2,\"birthday\":1412092800000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"}";
        String jsonlist = "[{\"age\":2,\"birthday\":1412092800000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"},{\"age\":2,\"birthday\":1420041600000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"}]";

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        ObjectMapper mapper = new ObjectMapper();

        // JSON转User类
        User user = null;
        try {
            user = mapper.readValue(json, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // User{name='silion', age=0, birthday=Wed Oct 01 00:00:00 GMT+08:00 2014, email='silion@qq.com'}
        System.out.println(user);

        // JSON转集合
        List<User> users = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
            users = (List<User>) mapper.readValue(jsonlist, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // [User{name='silion', age=0, birthday=Wed Oct 01 00:00:00 GMT+08:00 2014, email='silion@qq.com'}, User{name='silion', age=0, birthday=Thu Jan 01 00:00:00 GMT+08:00 2015, email='silion@qq.com'}]
        System.out.println(users);
    }
}
