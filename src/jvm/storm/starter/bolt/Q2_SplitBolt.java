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
import java.util.HashMap;
import java.util.Map;


public class Q2_SplitBolt implements IBasicBolt {
  ArrayList<String> map = new ArrayList<String>(){{
    add("rt");
    add("a");
    add("about");
    add("above");
    add("after");
    add("again");
    add("against");
    add("all");
    add("am");
    add("an");
    add("and");
    add("any");
    add("are");
    add("aren't");
    add("as");
    add("at");
    add("be");
    add("because");
    add("been");
    add("before");
    add("being");
    add("below");
    add("between");
    add("both");
    add("but");
    add("by");
    add("can't");
    add("cannot");
    add("could");
    add("couldn't");
    add("did");
    add("didn't");
    add("do");
    add("does");
    add("doesn't");
    add("doing");
    add("don't");
    add("down");
    add("during");
    add("each");
    add("few");
    add("for");
    add("from");
    add("further");
    add("had");
    add("hadn't");
    add("has");
    add("hasn't");
    add("have");
    add("haven't");
    add("having");
    add("he");
    add("he'd");
    add("he'll");
    add("he's");
    add("her");
    add("here");
    add("here's");
    add("hers");
    add("herself");
    add("him");
    add("himself");
    add("his");
    add("how");
    add("how's");
    add("i");
    add("i'd");
    add("i'll");
    add("i'm");
    add("i've");
    add("if");
    add("in");
    add("into");
    add("is");
    add("isn't");
    add("it");
    add("it's");
    add("its");
    add("itself");
    add("let's");
    add("me more");
    add("most");
    add("mustn't");
    add("my");
    add("myself");
    add("no");
    add("nor");
    add("not");
    add("of");
    add("off");
    add("on");
    add("once");
    add("only");
    add("or");
    add("other");
    add("ought");
    add("our");
    add("ours");
    add("ourselves");
    add("out");
    add("over");
    add("own");
    add("same");
    add("shan't");
    add("she");
    add("she'd");
    add("she'll");
    add("she's");
    add("should");
    add("shouldn't");
    add("so");
    add("some");
    add("such");
    add("than");
    add("that");
    add("that's");
    add("the");
    add("their");
    add("theirs");
    add("them");
    add("themselves");
    add("then");
    add("there");
    add("there's");
    add("these");
    add("they");
    add("they'd");
    add("they'll");
    add("they're");
    add("they've");
    add("this");
    add("those");
    add("through");
    add("to");
    add("too");
    add("under");
    add("until");
    add("up");
    add("very");
    add("was");
    add("wasn't");
    add("we");
    add("we'd");
    add("we'll");
    add("we're");
    add("we've");
    add("were");
    add("weren't");
    add("what");
    add("what's");
    add("when");
    add("when's");
    add("where");
    add("where's");
    add("which");
    add("while");
    add("who");
    add("who's");
    add("whom");
    add("why");
    add("why's");
    add("with");
    add("won't");
    add("would");
    add("wouldn't");
    add("you");
    add("you'd");
    add("you'll");
    add("you're");
    add("you've");
    add("your");
    add("yours");
    add("yourself");
    add("yourselves");
  }};

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
    Status status=(Status) tuple.getValueByField("tweet");
//    System.out.println(status);
    String s=status.getText().replaceAll("\r|\n", " ");
    String[] split=s.split(" ");

    //clean
    for(int i=split.length-1;i>=0;i--) {
      if(split[i].length()>=1 && ( split[i].charAt(0) == '#' || split[i].charAt(0) == '@') ) split[i]="";
      if(split[i].length() >= 8 && "https://".equals(split[i].substring(0,8))) split[i]="";
      split[i] = split[i].replaceAll("[^a-zA-Z\']","").toLowerCase();
      if(map.contains(split[i])) split[i]="";
    }

    //print
    System.out.print("Q2_SplitBolt: ");
    for(int i=0;i<split.length;i++){
      System.out.print(split[i]+" ");
      if(split[i].length()>0) collector.emit(new Values(status));
    }
    System.out.println();


  }


  @Override
  public void prepare(Map map, TopologyContext topologyContext) {

  }

  @Override
  public void cleanup(){

  }

}
