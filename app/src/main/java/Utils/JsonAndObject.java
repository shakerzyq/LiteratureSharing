package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import domain.PwdProtect;
import domain.User;
import domain.WorkForFind;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 周杨清
 * @time 2020/6/6
 * 实体类对象和json之间的转化
 */
public class JsonAndObject {

    //将实体类对象转为json
    public static RequestBody toJson(Object object){
        Gson gson = new Gson();
        String gsons = gson.toJson(object);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                ,gsons);
        return requestBody;
    }

    //将json转化为实体类对象
    public static PwdProtect toPwdProtect(String responseData){
        Gson gson = new Gson();
        PwdProtect pwdProtect = gson.fromJson(responseData, new TypeToken<PwdProtect>() {}.getType());
        return pwdProtect;
    }

    //将json转化为实体类对象
    public static User toUser(String responseData){
        Gson gson = new Gson();
        User user = gson.fromJson(responseData, new TypeToken<User>() {}.getType());
        return user;
    }
    public static List<WorkForFind> toWorks(String responseData){
        Gson gson = new Gson();
        List<WorkForFind> workForFinds = gson.fromJson(responseData, new TypeToken<WorkForFind>() {}.getType());
        return workForFinds;
    }
}
