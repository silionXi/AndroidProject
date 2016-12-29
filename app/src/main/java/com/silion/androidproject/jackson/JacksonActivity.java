package com.silion.androidproject.jackson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silion.androidproject.BaseActivity;
import com.silion.androidproject.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonActivity extends BaseActivity {

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

        // User类转JSON
        String json = object2Json(user1);;
        // User类转JSON : {"birthday":"2014年09月30日","name":"silion","mail":"silion@qq.com"}
        System.out.println("User类转JSON : " + json);

        // Map转JSON
        Map<String, User> map = new HashMap<>();
        map.put("silion", user1);
        String jsonmap = object2Json(map);
        // Map转JSON : {"silion":{"birthday":"2014年09月30日","name":"silion","mail":"silion@qq.com"}}
        System.out.println("Map转JSON : " + jsonmap);

        // List转JSON
        String jsonlist = object2Json(users);
        // List转JSON : [{"birthday":"2014年09月30日","name":"silion","mail":"silion@qq.com"},{"birthday":"2014年12月31日","name":"silion","mail":"silion@qq.com"}]
        System.out.println("List转JSON : " + jsonlist);
    }

    /**
     * 将一个object转换为json, 可以使一个java对象，也可以使集合
     *
     * @param object
     * @return
     */
    private String object2Json(Object object) {
        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public void json2Java(View view) {
        String json = "{\"age\":2,\"birthday\":1412092800000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"}";
        String jsonmap = "{\"silion\":{\"birthday\":\"2014年09月30日\",\"name\":\"silion\",\"mail\":\"silion@qq.com\"}}";
        String jsonlist = "[{\"age\":2,\"birthday\":1412092800000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"},{\"age\":2,\"birthday\":1420041600000,\"mail\":\"silion@qq.com\",\"name\":\"silion\"}]";

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        
        // JSON转Class
        User user = json2Class(json, User.class);
        // JSON转Class : User{name='silion', age=0, birthday=Wed Oct 01 00:00:00 GMT+08:00 2014, email='silion@qq.com'}
        System.out.println("JSON转Class : " + user);

        // JSON转Map
        Map<String, User> map = json2Map(jsonmap);
        // JSON转Map : {silion=User{name='silion', age=0, birthday=Tue Sep 30 08:00:00 GMT+08:00 2014, email='silion@qq.com'}}
        System.out.println("JSON转Map : " + map);

        // JSON转List
        List<User> users = json2List(jsonlist);
        // JSON转List : [User{name='silion', age=0, birthday=Wed Oct 01 00:00:00 GMT+08:00 2014, email='silion@qq.com'}, User{name='silion', age=0, birthday=Thu Jan 01 00:00:00 GMT+08:00 2015, email='silion@qq.com'}]
        System.out.println("JSON转List : " + users);

        // JSON转List2
        List<User> users2 = json2List2(jsonlist);
        // JSON转List2 : [User{name='silion', age=0, birthday=Wed Oct 01 00:00:00 GMT+08:00 2014, email='silion@qq.com'}, User{name='silion', age=0, birthday=Thu Jan 01 00:00:00 GMT+08:00 2015, email='silion@qq.com'}]
        System.out.println("JSON转List2 : " + users2);

    }

    private List<User> json2List2(String jsonlist) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = null;
        try {
            users = mapper.readValue(jsonlist, new TypeReference<List<User>>(){
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<User> json2List(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
            users = mapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private Map<String, User> json2Map(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, User> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, User>>(){
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private <T> T json2Class(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(json, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
