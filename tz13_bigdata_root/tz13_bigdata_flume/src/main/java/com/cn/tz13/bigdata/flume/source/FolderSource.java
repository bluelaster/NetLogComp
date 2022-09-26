package com.cn.tz13.bigdata.common.source;

import com.cn.tz13.bigdata.common.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.io.File.separator;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/21 16:15
 * @description :  Business Process Class
 */
public class FolderSource extends AbstractSource implements Configurable, PollableSource {

    private static final Logger LOG = Logger.getLogger(FolderSource.class);

    String folderDir;                //文件监控目录
    String succDir;                  //文件处理成功存放目录
    private int fileNum;             //每批最多处理的文件数量
    private List<File> fileList;     //存放每批需要处理的文件集合
    private List<Event> eventList;

    //读取配置文件
    @Override
    public void configure(Context context) {
        eventList = new ArrayList<Event>();
        folderDir = context.getString("folderDir");
        succDir = context.getString("succDir");
        fileNum = context.getInteger("fileNum");
        LOG.info("----------------------初始化参数----------------------");
        LOG.info("folderDir=" + folderDir + " , succDir=" + succDir + " , fileNum=" + fileNum);
    }

    //循环处理业务
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        //调试阶段，看日志
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            LOG.info("=================Source被执行================");

            //读取文件
            List<File> files = (List<File>) FileUtils.listFiles(new File(folderDir), new String[]{"txt"}, true);

            //文件截取
            int fileCount = files.size();
            if (fileCount > fileNum) {
                fileList = files.subList(0, fileNum);
            } else {
                fileList = files;
            }

            LOG.info("fileList.size() = " + fileList.size());
            if (fileList.size() > 0) {
                //遍历读取文件
                for (int i = 0; i < fileList.size(); i++) {
                    File file = fileList.get(i);
                    //原始文件名
                    String fileName = file.getName();
                    LOG.info("fileNum = " + fileNum);

                    //定义一个新的文件目录，用来存放移走的文件
                    String fileDirNew = succDir + separator + DateUtils.getDate("yyyy-MM-dd");
                    //新文件的绝对路径文件名 = 新目录 + 原来的文件名
                    String fileNameNew = fileDirNew + separator +  fileName;
                    //LOG.info("fileDirNew = " + fileDirNew);
                    //LOG.info("fileNameNew = " + fileNameNew);

                    try {
                        //如果文件存在
                        if (new File(fileNameNew).exists()) {
                            //如果同名文件存在，不做处理。
                            LOG.info("文件存在，不做处理。");
                        } else {
                            //如果不存在，处理当前文件。
                            LOG.info("文件不存在，需要处理。");
                            List<String> lines = FileUtils.readLines(file);

                            lines.forEach(line -> {
                                // Receive new data
                                Event e = new SimpleEvent();
                                e.setBody(line.getBytes());
                                //附加元数据到消息头
                                Map headers = new HashMap<String, String>();
                                headers.put("fileName", fileName);
                                headers.put("absolute_fileName", fileNameNew);
                                e.setHeaders(headers);

                                eventList.add(e);
                            });
                            LOG.info("开始移动文件。");
                            //文件处理完成后，将解析完成的文件移动到成功的目录下。
                            FileUtils.moveToDirectory(file, new File(fileDirNew), true);
                            LOG.info("文件移动完成。");
                        }
                    } catch (IOException ie) {
                        FileUtils.moveToDirectory(file, new File("fail_dir"), true);
                        LOG.error(null,ie);
                    }
                }
                getChannelProcessor().processEventBatch(eventList);
                LOG.info("批量推送数据到channel" + eventList.size() + "成功。");
                eventList.clear();
            }
            status = Status.READY;
        } catch (Exception e) {
            // Log exception, handle individual exceptions as needed
            status = Status.BACKOFF;
            // re-throw all Errors
            LOG.error(null,e);
        } finally {
        }
        return status;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }
}
