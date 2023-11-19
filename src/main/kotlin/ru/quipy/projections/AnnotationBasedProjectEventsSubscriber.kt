//package ru.quipy.projections
//
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//import ru.quipy.streams.annotation.AggregateSubscriber
//import ru.quipy.streams.annotation.SubscribeEvent
//import ru.quipy.api.project.*
//
//@Service
//@AggregateSubscriber(
//    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
//)
//class AnnotationBasedProjectEventsSubscriber {
//
//    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedProjectEventsSubscriber::class.java)
//
//
//}