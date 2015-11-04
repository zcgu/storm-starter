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
 
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
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


public class Q2_SelectBolt_helper_selecthashtag implements IBasicBolt {


  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }


  @Override
  public void execute(Tuple tuple, BasicOutputCollector collector) {
            Status status = (Status) tuple.getValueByField("tweet");

            HashtagEntity[] hashtag = status.getHashtagEntities();
            for (int i = 0; i < hashtag.length; i++) {
                System.out.println("Q2_SelectBolt hashtag: " + hashtag[i].getText());
                collector.emit(new Values(hashtag[i].getText().toLowerCase()));
            }
  }


  @Override
  public void prepare(Map map, TopologyContext topologyContext) {
  }

  @Override
  public void cleanup() {
  }
}
