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

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import storm.starter.util.TupleHelpers;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Q2_PrinterBolt implements IRichBolt {

  FileWriter fstream;
  BufferedWriter out;
  String fname ;
  Map<String, Integer> map = new HashMap<String, Integer>();
  ArrayList<Integer> list = new ArrayList<Integer>();
  int OutputFrequency = -1;

  public Q2_PrinterBolt(int OutputFrequency, String fname){
    this.OutputFrequency = OutputFrequency;
    this.fname = fname;
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer ofd) {
  }

  @Override
  public void execute(Tuple tuple) {
    if(TupleHelpers.isTickTuple(tuple)) {
      if(list.size() > 0) {
        Collections.sort(list);
        int middle = list.get(list.size() * 1/2);
        ArrayList<String> tmp = new ArrayList<String>();
        try {
          for(Map.Entry<String, Integer> entry : map.entrySet())
            if(entry.getValue() > middle)
              tmp.add(entry.getKey());
          Collections.sort(tmp);
          for(int i=0;i<tmp.size();i++) out.write(tmp.get(i) + " ");
          out.newLine();
          out.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
        map.clear();
        list.clear();
      }
    }
    else {
      ArrayList<String> tmp1 = (ArrayList<String>) tuple.getValueByField("word");
      ArrayList<Integer> tmp2 = (ArrayList<Integer>) tuple.getValueByField("count");
      for(int i=0;i<tmp1.size();i++){
        map.put(tmp1.get(i),tmp2.get(i));
        list.add(tmp2.get(i));
      }
    }
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

  @Override
  public Map<String, Object> getComponentConfiguration() {
    Map<String, Object> conf = new HashMap<String, Object>();
    conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, OutputFrequency);
    return conf;
  }


}
