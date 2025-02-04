package top.darkflow.mcctf.Database;

import top.darkflow.mcctf.Model.CTFDocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTFCache {
    public static Map<String , ArrayList<String>> teamCache= new HashMap<>();
    public static Map<String ,ArrayList<String>> playerCache= new HashMap<>();
    public static Map<String ,ArrayList<CTFDocker>> challengeCache= new HashMap<>();//题目启动缓存，用于判断队伍已经启动了那些docker
    public static int CurrentPort=40000;


}
