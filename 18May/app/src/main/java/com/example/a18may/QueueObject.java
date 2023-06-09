//Some of the code was inspired and modified from https://www.youtube.com/watch?v=dS44jZyj5gU&t=4175s 

package com.example.a18may;

public class QueueObject implements Comparable<QueueObject>{
    protected Vertex vertex;
    protected double priority;

    public QueueObject(Vertex v, double p)
    {
        this.vertex = v;
        this.priority= p;
    }



    @Override
    public int compareTo(QueueObject o) {
        if(this.priority == o.priority)
        {
            return 0;
        }
        else if(this.priority > o.priority)
        {
            return 1;
        }
        else{
            return -1;
        }
    }
}
