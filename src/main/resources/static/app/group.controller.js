(function () {
    'use strict';

    angular
        .module('app')
        .controller('GroupController', GroupController);

    GroupController.$inject = ['$http'];

    function GroupController($http) {
        var vm = this;

        vm.groups = [];
        vm.getAll = getAll;
        vm.leaveGroup = leaveGroup;
        vm.setUserNames = setUserNames;
        vm.setTaskNames = setTaskNames;
        vm.createGroup = createGroup;
        vm.deleteParticipant = deleteParticipant;
        vm.ownersJson;
        vm.tasksJson;
        vm.addParticipant = addParticipant;
        vm.owners;
        vm.getOwners = getOwners;
        vm.inspectGroup = inspectGroup;
        vm.addTask = addTask;
        vm.getTasks = getTasks;
        vm.deleteTask = deleteTask;
        vm.tasks;
        // vm.getDone = getDone;
        // vm.getUnDone = getUnDone;
        // vm.toggledone = toggledone;
        // vm.deleteTask = deleteTask;


        init();

        function init(){
            getAll();
        }

        function inspectGroup(id) {

        }

        function getTasks() {
            var select = document.getElementById("deleteTaskGroupSelect").value;
            for (var i = 0; i < vm.groups.length; i++) {
                if (vm.groups[i].id === Number(select)) {
                    vm.tasks = setTaskNames(vm.groups[i].tasks);
                }
            }
        }

        function addTask() {
            var url = "/groups/newtask";
            var data = new FormData();
            data.append('title', document.getElementById('titleInput').value);
            data.append('desc', "brak");
            data.append('date', document.getElementById('endDate').value);
            data.append('id', Number(document.getElementById('newTaskGroupSelect').value));
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open("POST", url, false);
            xmlHttp.send(data);
            getAll();
        }

        function deleteTask() {
            var url = "/groups/deleteTask";
            var data = new FormData();
            data.append('id', Number(document.getElementById('deleteTaskGroupSelect').value));
            data.append('indeks', document.getElementById('deleteTaskSelect').selectedIndex);
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open("POST", url, false);
            xmlHttp.send(data);
            getAll();
        }

        function deleteParticipant() {
            var url = "/groups/deleteParticipant";
            var data = new FormData();
            data.append('id', Number(document.getElementById('deleteParticipantGroupSelect').value));
            data.append('name', document.getElementById('deleteParticipantselect').value);
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open("POST", url, false);
            xmlHttp.send(data);
            getAll();

        }

        function getOwners() {
            var select = document.getElementById("deleteParticipantGroupSelect").value;
            for (var i = 0; i < vm.groups.length; i++) {
                if (vm.groups[i].id === Number(select)) {
                    vm.owners = setUserNames(vm.groups[i].participants);
                }
            }
        }

        function addParticipant() {
            var id = Number(document.getElementById('newParticipantSelect').value);
            var name = document.getElementById('participantInput').value;
            var xmlHttp = new XMLHttpRequest();
            var data = new FormData();
            data.append('id', id);
            data.append('name', name);
            var url = "https://localhost:8443/groups/addParticipant";
            xmlHttp.open("POST", url, false);
            xmlHttp.send(data);
            getAll();
        }

        function leaveGroup(id) {
            var xmlHttp = new XMLHttpRequest();
            var data = new FormData();
            data.append('id', id);
            var url = "https://localhost:8443/groups/leaveGroup";
            xmlHttp.open( "POST", url, false );
            xmlHttp.send(data);
            getAll();
        }

        function createGroup() {
            var name = document.getElementById('groupNameInput').value;
            var xmlHttp = new XMLHttpRequest();
            var data = new FormData();
            data.append('name', name);
            var url = "https://localhost:8443/groups/createGroup";
            xmlHttp.open( "POST", url, false );
            xmlHttp.send(data);
            getAll();
        }

        function setUserNames(id) {
            var string = id.toString().split(",");
            var names = [];
            for (var i = 0; i < string.length; i++) {
                if (vm.ownersJson[string[i]] != null) {
                    names.push(vm.ownersJson[string[i]]);
                }
            }
            return names;
        }

        function setTaskNames(id) {
            var string = id.toString().split(",");
            var names = [];
            for (var i = 0; i < string.length; i++) {
                if (vm.tasksJson[string[i]] != null) {
                    names.push(vm.tasksJson[string[i]]);
                }
            }
            return names;
        }

        // function getUnDone() {
        //     var url = "/groups/showUnDoneOnly";
        //     $http.get(url).then(function (response) {
        //         vm.tasks = response.data;
        //     });
        // }
        //
        // function getDone() {
        //     var url = "/groups/showDoneOnly";
        //     $http.get(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }
        //
        // function deleteTask(id){
        //     var url = "/groups/delete/" + id;
        //     $http.post(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }
        //
        // function toggledone(id) {
        //     var url = "/groups/toggleDone/" + id;
        //     $http.post(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }

        function getAll(){
            var url = "/groups/getOwnerNames";
            $http.get(url).then(function (response) {
                vm.ownersJson = response.data;
            });

            url = "/tasks/getTasksNames";
            $http.get(url).then(function (response) {
                vm.tasksJson = response.data;
            });

            url = "/groups/mygroups";
            var groupsPromise = $http.get(url);
            groupsPromise.then(function(response){
                vm.groups = response.data;
            });
            getTasks();
        }
    }
})();
