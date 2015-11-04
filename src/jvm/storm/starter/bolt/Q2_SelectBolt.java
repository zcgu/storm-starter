/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package storm.starter.bolt;
 
import backtype.storm.task.OutputCollector;
import backtype.storm.task.ShellBolt;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Q2_SelectBolt  implements IBasicBolt {

  int count_total=0,count=0;
  FileWriter fstream;
  BufferedWriter out;
  String fname ;
  ArrayList<String> hashtags = null;
  int number;

    public Q2_SelectBolt(String fname){
        this.fname = fname;
    }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("tweet"));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }


  @Override
  public void execute(Tuple tuple, BasicOutputCollector collector) {


 //   System.out.println("Total number is: "+ ++count_total);

    if("change".equals(tuple.getSourceComponent())) {
        hashtags = (ArrayList<String>) tuple.getValueByField("hashtags");
        number = (Integer) tuple.getValueByField("number");
        count=0;
        count_total=0;
        try {
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    else if ("twitter".equals(tuple.getSourceComponent())) {
        if (hashtags != null) {
            Status status = (Status) tuple.getValueByField("tweet");

            int flag = 0;
            HashtagEntity[] hashtag = status.getHashtagEntities();
//            for (int i = 0; i < hashtag.length; i++)
//                System.out.println("Q2_SelectBolt hashtag: " + hashtag[i].getText().replaceAll("\r|\n", " "));

            for (int i = 0; i < hashtag.length; i++)
                if (hashtags.contains(hashtag[i].getText().toLowerCase())) flag = 1;

            if (status.getUser().getFriendsCount() <= number && flag == 1) {
                System.out.println("Selected number is: " + ++count);
                System.out.println("Q2_SelectBolt: "+status.getText().replaceAll("\r|\n", " "));
                try {
                    out.write(status.getText().replaceAll("\r|\n", " "));
                    out.newLine();
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                collector.emit(new Values(status));
            }
        }
    }
  }


  @Override
  public void prepare(Map map, TopologyContext topologyContext) {

    try {
      fstream = new FileWriter(fname);
    } catch (IOException e) {
      e.printStackTrace();
    }
    out = new BufferedWriter(fstream);
  }

  @Override
  public void cleanup(){
    try {
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
