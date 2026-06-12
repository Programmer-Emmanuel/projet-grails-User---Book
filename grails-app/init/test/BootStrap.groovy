package test

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        initData()
    }

    @Transactional
    void initData (){
        if (!Role.findByAuthority('ROLE_USER')) {
            new Role(authority: 'ROLE_USER')
                    .save(flush: true, failOnError: true)
        }
    }

    def destroy = {
    }
}