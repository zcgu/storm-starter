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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Q2_ChangeBolt extends BaseBasicBolt {

      ArrayList<String> hashtags;
      int number=0;
      int OutputFrequency = 30;
        int flag=0;

      @Override
      public void execute(Tuple tuple, BasicOutputCollector collector) {
        if("hashtags".equals(tuple.getSourceComponent())) {
          hashtags = (ArrayList<String>) tuple.getValueByField("hashtags");
            if(flag<2) flag++;
        }
        else if ("number".equals(tuple.getSourceComponent())) {
          number = (Integer) tuple.getValueByField("number");
            if(flag<2) flag++;
        }
        else if(TupleHelpers.isTickTuple(tuple)){
          collector.emit(new Values(hashtags, number));
          System.out.println("Q2_Changebolt: " + hashtags + " " + number);
        }

          if(flag==2){
            collector.emit(new Values(hashtags, number));
            System.out.println("Q2_Changebolt: " + hashtags + " " + number);
            flag=3;
        }
      }

      @Override
      public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("hashtags","number"));
      }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, OutputFrequency);
        return conf;
    }

    }
