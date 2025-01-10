package com.chatgpt.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

public class JVMInfoUtil {

    public static Map<String, Object> getMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryMXBean.getNonHeapMemoryUsage();

        Map<String, Object> memoryInfo = new HashMap<>();
        memoryInfo.put("heapInit", heapUsage.getInit());
        memoryInfo.put("heapUsed", heapUsage.getUsed());
        memoryInfo.put("heapCommitted", heapUsage.getCommitted());
        memoryInfo.put("heapMax", heapUsage.getMax());
        memoryInfo.put("nonHeapInit", nonHeapUsage.getInit());
        memoryInfo.put("nonHeapUsed", nonHeapUsage.getUsed());
        memoryInfo.put("nonHeapCommitted", nonHeapUsage.getCommitted());
        memoryInfo.put("nonHeapMax", nonHeapUsage.getMax());

        return memoryInfo;
    }

    public static Map<String, Object> getThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put("threadCount", threadMXBean.getThreadCount());
        threadInfo.put("peakThreadCount", threadMXBean.getPeakThreadCount());
        threadInfo.put("daemonThreadCount", threadMXBean.getDaemonThreadCount());
        threadInfo.put("totalStartedThreadCount", threadMXBean.getTotalStartedThreadCount());

        return threadInfo;
    }

    public static Map<String, Object> getSystemInfo() {
        Runtime runtime = Runtime.getRuntime();

        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("availableProcessors", runtime.availableProcessors());
        systemInfo.put("freeMemory", runtime.freeMemory());
        systemInfo.put("maxMemory", runtime.maxMemory());
        systemInfo.put("totalMemory", runtime.totalMemory());

        return systemInfo;
    }
}
