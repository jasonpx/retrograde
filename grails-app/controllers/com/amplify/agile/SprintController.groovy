package com.amplify.agile

import com.amplify.agile.Sprint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SprintController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if(!params.sort) params.sort = "name"
        if(!params.order) params.order = "asc"
        respond Sprint.list(params), model:[sprintInstanceCount: Sprint.count()]
    }

    def show(Sprint sprintInstance) {
        respond sprintInstance
    }

    def create() {
        respond new Sprint(params)
    }

    @Transactional
    def save(Sprint sprintInstance) {
        if (sprintInstance == null) {
            notFound()
            return
        }

        if (sprintInstance.hasErrors()) {
            respond sprintInstance.errors, view:'create'
            return
        }

        sprintInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'sprint.label', default: 'Sprint'), sprintInstance.id])
                redirect sprintInstance
            }
            '*' { respond sprintInstance, [status: CREATED] }
        }
    }

    def edit(Sprint sprintInstance) {
        respond sprintInstance
    }

    @Transactional
    def update(Sprint sprintInstance) {
        if (sprintInstance == null) {
            notFound()
            return
        }

        if (sprintInstance.hasErrors()) {
            respond sprintInstance.errors, view:'edit'
            return
        }

        sprintInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Sprint.label', default: 'Sprint'), sprintInstance.id])
                redirect sprintInstance
            }
            '*'{ respond sprintInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Sprint sprintInstance) {

        if (sprintInstance == null) {
            notFound()
            return
        }

        sprintInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Sprint.label', default: 'Sprint'), sprintInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
