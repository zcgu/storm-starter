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
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class PrinterBolt_Group16_Q2 implements IRichBolt {

  int count=0;
  FileWriter fstream;
  BufferedWriter out;
  String fname = "QuestionA2_data_1";

  @Override
  public void declareOutputFields(OutputFieldsDeclarer ofd) {
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }


  @Override
  public void execute(Tuple tuple) {
    if(count<1000000){
      String s=tuple.toString();
      if("source: twitter:".equals(s.substring(0,16))) count++;
      s = s.replaceAll("\r|\n", "");
      System.out.println("Total number is: "+ count);

      Status status=(Status) tuple.getValueByField("tweet");
      //System.out.println(status);
      HashtagEntity[] hashtag = status.getHashtagEntities();
      for (int i=0;i<hashtag.length;i++) System.out.println(hashtag[i].getText());

      try {
          if("source: twitter:".equals(s.substring(0,16))) out.newLine();
          out.write(s);
          out.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else System.out.println("Total number is: "+ (count));
  }


  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

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
