package com.nop.guawa;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by yangzijun on 17-2-22.
 */
public class ObjectsDemo {

    class Item {
       private int id;
       private String name;

       @Override
        public int hashCode(){
           return Objects.hashCode(id,name);
       }

       @Override
        public String toString(){
            return MoreObjects.toStringHelper(this).add("id",id).add("name",name).toString();
       }


        @Override
        public boolean equals(Object o){
            if(!(o instanceof Item)){
                return false;
            }
            Item other=(Item)o;
            return Objects.equal(id,other.id)&&Objects.equal(name,other.name);
        }
    }

    public static void main(String[] args) {
        ObjectsDemo demo=new ObjectsDemo();
        Item item=demo.new Item();
        Item item1=demo.new Item();
        item.id=1;
        item.name="item";
        item1.id=1;
        item1.name="item";
        System.out.println("item="+item+",item1="+item1);
        System.out.println(item.equals(item1));
    }
}
