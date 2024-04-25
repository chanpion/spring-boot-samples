package com.chanpion.samples.quartz.controller;

import com.chanpion.samples.quartz.entity.QuartzConfigDTO;
import com.chanpion.samples.quartz.service.QuartzJobService;
import com.chanpion.samples.quartz.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/1/4 11:04
 */
@RestController
@Slf4j
public class JobController {
    @Resource
    private QuartzJobService quartzJobService;
    /**
     * 添加新任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/addJob")
    public Object addJob(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.addJob(configDTO.getJobClass(), configDTO.getJobName(), configDTO.getGroupName(), configDTO.getCronExpression(), configDTO.getParam());
        return HttpStatus.OK;
    }

    /**
     * 暂停任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/pauseJob")
    public Object pauseJob(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.pauseJob(configDTO.getJobName(), configDTO.getGroupName());
        return HttpStatus.OK;
    }

    /**
     * 恢复任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/resumeJob")
    public Object resumeJob(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.resumeJob(configDTO.getJobName(), configDTO.getGroupName());
        return HttpStatus.OK;
    }

    /**
     * 立即运行一次定时任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/runOnce")
    public Object runOnce(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.runOnce(configDTO.getJobName(), configDTO.getGroupName());
        return HttpStatus.OK;
    }

    /**
     * 更新任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/updateJob")
    public Object updateJob(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.updateJob(configDTO.getJobName(), configDTO.getGroupName(), configDTO.getCronExpression(), configDTO.getParam());
        return HttpStatus.OK;
    }

    /**
     * 删除任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/deleteJob")
    public Object deleteJob(@RequestBody QuartzConfigDTO configDTO) {
        quartzJobService.deleteJob(configDTO.getJobName(), configDTO.getGroupName());
        return HttpStatus.OK;
    }

    /**
     * 启动所有任务
     *
     * @return
     */
    @RequestMapping("/startAllJobs")
    public Object startAllJobs() {
        quartzJobService.startAllJobs();
        return HttpStatus.OK;
    }

    /**
     * 暂停所有任务
     *
     * @return
     */
    @RequestMapping("/pauseAllJobs")
    public Object pauseAllJobs() {
        quartzJobService.pauseAllJobs();
        return HttpStatus.OK;
    }

    /**
     * 恢复所有任务
     *
     * @return
     */
    @RequestMapping("/resumeAllJobs")
    public Object resumeAllJobs() {
        quartzJobService.resumeAllJobs();
        return HttpStatus.OK;
    }

    /**
     * 关闭所有任务
     *
     * @return
     */
    @RequestMapping("/shutdownAllJobs")
    public Object shutdownAllJobs() {
        quartzJobService.shutdownAllJobs();
        return HttpStatus.OK;
    }

    @GetMapping("getAllJobs")
    public Object getAllJobs() {
        return quartzJobService.getAllJobs();
    }


}
