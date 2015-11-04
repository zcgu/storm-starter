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
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import storm.starter.util.TupleHelpers;
import twitter4j.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Q2_CountBolt extends BaseBasicBolt {

  Map<String, Integer> map = new HashMap<String, Integer>();
    int OutputFrequency = -1;

    public Q2_CountBolt(int OutputFrequency){
        this.OutputFrequency = OutputFrequency;
    }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("word","count"));
  }


  @Override
  public void execute(Tuple tuple, BasicOutputCollector collector) {
      if(TupleHelpers.isTickTuple(tuple)){
          ArrayList<String> tmp1 = new ArrayList<String>();
          ArrayList<Integer> tmp2 = new ArrayList<Integer>();
          for (Map.Entry<String, Integer> entry : map.entrySet()) {
              System.out.println("Q2_CountBolt: " + "Key = " + entry.getKey() + ", Value = " + entry.getValue());
              tmp1.add(entry.getKey());
              tmp2.add(entry.getValue());
          }
          collector.emit(new Values(tmp1,tmp2));
          map.clear();
      }
      else {
          String word = (String) tuple.getValueByField("word");

          Integer count = map.get(word);
          if (count == null) count = 0;
          count++;
          map.put(word, count);
      }
  }

  @Override
  public void cleanup(){
  }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, OutputFrequency);
        return conf;
    }

}
