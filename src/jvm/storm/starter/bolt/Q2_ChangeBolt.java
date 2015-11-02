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
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.Map;



public class Q2_ChangeBolt extends BaseBasicBolt {

      int flag=0;
      ArrayList<String> hashtags;
      int number;

      @Override
      public void execute(Tuple tuple, BasicOutputCollector collector) {
        if("hashtags".equals(tuple.getSourceComponent())) {
          hashtags = (ArrayList<String>) tuple.getValueByField("hashtags");
          flag++;
        }
        else if ("number".equals(tuple.getSourceComponent())) {
          number = (Integer) tuple.getValueByField("number");
          flag++;
        }

        if(flag==2){
          flag=0;
          collector.emit(new Values(hashtags, number));
          System.out.println("Q2_Changebolt: " + hashtags + " " + number);
        }

      }

      @Override
      public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("hashtags","number"));
      }

    }