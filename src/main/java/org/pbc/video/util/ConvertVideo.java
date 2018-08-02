package org.pbc.video.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConvertVideo {



    public int  otherToMp4(String oldPath,String newPath) {

        String back = oldPath.substring(oldPath.lastIndexOf("."),oldPath.length());
        String begin = oldPath.substring(0,oldPath.lastIndexOf("."));
        System.out.println(back+"--"+begin);

        if ("mp4".equals(back)||"mkv".equals(back)){

            processFfmpegOther(oldPath,begin+".mp4");
        }

       return cutImg(oldPath,newPath);
    }

    /**
     * ffmpeg将其他格式转换成mp4格式文件（未指定其他任何参数）
     * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     * @param srcVideoPath 视频文件(原)
     * @param tarVideoPath 视频文件(新)
     * @return
     */
    public static boolean processFfmpegOther(String srcVideoPath,String tarVideoPath) {
        if (!checkfile(srcVideoPath)) {

            return false;
        }

        List<String> commend = new java.util.ArrayList<String>();

//        String type =tarVideoPath.substring(tarVideoPath.lastIndexOf(".")+1, tarVideoPath.length());

        commend.add("d:\\ffmpeg\\ffmpeg.exe");

        commend.add( "-y");

        commend.add( "-i");

        commend.add(srcVideoPath);
        commend.add( " -f h264 ");

        commend.add(tarVideoPath);

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process process = builder.start();
            process.destroy();
            if (!checkfile(tarVideoPath)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkfile(String path){
        File file=new File(path);
        if (file.exists()) {
            return true;//如果文件存在
        }
        return false;
    }


    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private  int cutImg(String oldfilepath,String newPath) {


        try {
            Runtime runtime = Runtime.getRuntime();
            Process proce = null;
            //视频截图命令，封面图。  8是代表第8秒的时候截图
            File file = new File(oldfilepath);
            int s=0;
            if (file.length()>1*1024*1024){
                s=8;
            }

            String cmd = "";
            String cut = "     d:\\ffmpeg\\ffmpeg.exe   -i   "
                    + oldfilepath
                    + "   -y   -f   image2   -ss   "+s+"   -t   0.001   -s   320x180   "+newPath;
            String cutCmd = cmd + cut;
            proce = runtime.exec(cutCmd);


            return getVideoTime(oldfilepath);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取视频总时间
     */
    private int getVideoTime(String video_path) {
        List<String> commands = new ArrayList<>();
        commands.add("d:\\ffmpeg\\ffmpeg.exe");
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(stringBuilder.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                System.out.println("视频时长：" + time + "s , 开始时间：" + m.group(2) + ", 比特率：" + m.group(3) + "kb/s");
                return time;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 格式:"00:00:10.68"
    private int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            // 秒
            min += Integer.valueOf(strs[0]) * 60 * 60;
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }
}