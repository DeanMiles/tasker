(function () {
    'use strict';

    angular
        .module('app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$http'];

    function TaskController($http) {
        var vm = this;

        vm.tasks = [];
        vm.getAll = getAll;
        vm.getDone = getDone;
        vm.getUnDone = getUnDone;
        vm.toggledone = toggledone;
        vm.deleteTask = deleteTask;


        init();

        function init(){
            getAll();
        }

        function getUnDone() {
            var url = "/tasks/showUnDoneOnly";
            $http.get(url).then(function (response) {
                vm.tasks = response.data;
            });
        }

        function getDone() {
            var url = "/tasks/showDoneOnly";
            $http.get(url).then(function (response) {
               vm.tasks = response.data;
            });
        }

        function deleteTask(id){
            var url = "/tasks/delete/" + id;
            $http.post(url).then(function (response) {
               vm.tasks = response.data;
            });
        }
        
        function toggledone(id) {
            var url = "/tasks/toggleDone/" + id;
            $http.post(url).then(function (response) {
               vm.tasks = response.data;
            });
        }

        function getAll(){
            var url = "/tasks/mytasks";
            var tasksPromise = $http.get(url);
            tasksPromise.then(function(response){
                vm.tasks = response.data;
            });
        }
    }
})();
